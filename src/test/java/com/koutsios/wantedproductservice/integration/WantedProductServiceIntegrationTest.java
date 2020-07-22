package com.koutsios.wantedproductservice.integration;

import static com.koutsios.wantedproductservice.fixture.WantedProductFixture.aNewWantedProduct;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;
import com.koutsios.wantedproductservice.repository.WantedProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration Tests")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class WantedProductServiceIntegrationTest extends AbstractIntegrationTestWithMongoDb {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WantedProductRepository repository;

  @Test
  @DisplayName("Given a wishlist id and new wanted product then create new wanted product")
  void createNewWantedProduct_success() throws Exception {
    String wishlistId = "wishlistId";
    NewWantedProduct newWantedProduct = aNewWantedProduct();

    String response = createWantedProduct(wishlistId, newWantedProduct)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.wishlistId", is(wishlistId)))
        .andExpect(jsonPath("$.status", is("AVAILABLE")))
        .andReturn()
        .getResponse()
        .getContentAsString();
    WantedProduct wantedProductResponse = objectMapper.readValue(response, WantedProduct.class);

    WantedProduct wantedProductDb = repository.findById(wantedProductResponse.getId()).orElseThrow();
    assertEquals(wantedProductResponse.getWishlistId(), wantedProductDb.getWishlistId());
    assertEquals(wantedProductResponse.getName(), wantedProductDb.getName());
    assertEquals(wantedProductResponse.getProductDetailsLink(), wantedProductDb.getProductDetailsLink());
    assertEquals(wantedProductResponse.getStatus(), wantedProductDb.getStatus());
  }

  private ResultActions createWantedProduct(String wishlistId, NewWantedProduct newWantedProduct) throws Exception {
    return mockMvc.perform(post("/wanted/{wishlistId}", wishlistId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newWantedProduct)));
  }

}
