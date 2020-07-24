package com.koutsios.wantedproductservice.controller;

import static com.koutsios.wantedproductservice.fixture.WantedProductFixture.aNewWantedProduct;
import static com.koutsios.wantedproductservice.fixture.WantedProductFixture.aWantedProduct;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;
import com.koutsios.wantedproductservice.exception.WantedProductNotFoundException;
import com.koutsios.wantedproductservice.service.WantedProductService;
import java.util.Arrays;
import java.util.List;
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
    when(wantedProductService.createWantedProduct(anyString(), any(NewWantedProduct.class))).thenReturn(aWantedProduct("generatedId"));

    mockMvc.perform(post("/wanted/wishlist/wishlistId")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newWantedProduct)))
        .andExpect(status().isCreated());

    verify(wantedProductService).createWantedProduct(anyString(), any(NewWantedProduct.class));
  }

  @Test
  @DisplayName("GET Wanted Product - 200 Ok")
  void getWantedProduct_success() throws Exception {
    when(wantedProductService.getWantedProduct(anyString())).thenReturn(aWantedProduct("generatedId"));

    mockMvc.perform(get("/wanted/wantedProductId"))
        .andExpect(status().isOk());

    verify(wantedProductService).getWantedProduct(anyString());
  }

  @Test
  @DisplayName("GET Wanted Product - Invalid Wanted Product Id - 404 Not Found")
  void getWantedProduct_invalidWantedProductId_() throws Exception {
    when(wantedProductService.getWantedProduct(anyString())).thenThrow(new WantedProductNotFoundException("wantedProductId"));

    mockMvc.perform(get("/wanted/wantedProductId"))
        .andExpect(status().isNotFound());

    verify(wantedProductService).getWantedProduct(anyString());
  }

  @Test
  @DisplayName("GET Wanted Products by Wishlist Id - 200 Ok")
  void getAllWantedProducts_success() throws Exception {
    List<WantedProduct> wantedProducts = Arrays.asList(
        aWantedProduct("generatedId1"),
        aWantedProduct("generatedId2")
    );
    when(wantedProductService.getAllWantedProducts(anyString())).thenReturn(wantedProducts);

    mockMvc.perform(get("/wanted/wishlist/wishlistId"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)));

    verify(wantedProductService).getAllWantedProducts(anyString());
  }
}