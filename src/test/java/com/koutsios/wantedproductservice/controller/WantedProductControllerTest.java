package com.koutsios.wantedproductservice.controller;

import static com.koutsios.wantedproductservice.fixture.WantedProductFixture.aNewWantedProduct;
import static com.koutsios.wantedproductservice.fixture.WantedProductFixture.aWantedProduct;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;
import com.koutsios.wantedproductservice.service.WantedProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = WantedProductController.class)
@DisplayName("Wanted Product Controller method tests")
class WantedProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private WantedProductService wantedProductService;

  @Test
  @DisplayName("POST Wanted Product - 201 Created")
  void createNewWantedProduct() throws Exception {
    NewWantedProduct newWantedProduct = aNewWantedProduct();
    when(wantedProductService.createWantedProduct(anyString(), any(NewWantedProduct.class))).thenReturn(aWantedProduct());

    mockMvc.perform(post("/wanted/wishlistId")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newWantedProduct)))
        .andExpect(status().isCreated());

    verify(wantedProductService).createWantedProduct(anyString(), any(NewWantedProduct.class));
  }
}