package com.koutsios.wantedproductservice.integration;

import static com.koutsios.wantedproductservice.fixture.WantedProductFixture.aNewWantedProduct;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;
import com.koutsios.wantedproductservice.repository.WantedProductRepository;
import java.util.List;
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

  @Test
  @DisplayName("Given a wanted product id then return wanted product")
  void getWantedProduct_success() throws Exception {
    String wishlistId = "wishlistId";
    NewWantedProduct newWantedProduct = aNewWantedProduct();
    String wantedProductId = generatedWantedProductId(wishlistId, newWantedProduct);

    String response = mockMvc.perform(get("/wanted/{wantedProductId}", wantedProductId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.id", is(wantedProductId)))
        .andExpect(jsonPath("$.wishlistId", is(wishlistId)))
        .andExpect(jsonPath("$.name", is(newWantedProduct.getName())))
        .andExpect(jsonPath("$.productDetailsLink", is(newWantedProduct.getProductDetailsLink())))
        .andExpect(jsonPath("$.status", is(newWantedProduct.getStatus().toString())))
        .andReturn().getResponse().getContentAsString();
    WantedProduct wantedProductResponse = objectMapper.readValue(response, WantedProduct.class);

    WantedProduct wantedProductDb = repository.findById(wantedProductId).orElseThrow();
    assertEquals(wantedProductResponse.getWishlistId(), wantedProductDb.getWishlistId());
    assertEquals(wantedProductResponse.getName(), wantedProductDb.getName());
    assertEquals(wantedProductResponse.getProductDetailsLink(), wantedProductDb.getProductDetailsLink());
    assertEquals(wantedProductResponse.getStatus(), wantedProductDb.getStatus());
  }

  @Test
  @DisplayName("Given a wishlist id then return wanted products for that wishlist")
  void getAllWantedProducts_success() throws Exception {
    String wishlistId = "wishlistId2";
    NewWantedProduct newWantedProduct = aNewWantedProduct();
    // generate 2 wanted products
    generatedWantedProductId(wishlistId, newWantedProduct);
    generatedWantedProductId(wishlistId, newWantedProduct);
    // different wishlist id
    generatedWantedProductId("differentWishlistId", newWantedProduct);

    mockMvc.perform(get("/wanted/wishlist/{wishlistId}", wishlistId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  @DisplayName("Given a wanted product id when invalid then return 404 not found")
  void getWantedProduct_invalidWantedProductId_404NotFound() throws Exception {
    String wantedProductId = "InvalidId";

    mockMvc.perform(get("/wanted/{wantedProductId}", wantedProductId))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Could find Wishlist " + wantedProductId));
  }

  private ResultActions createWantedProduct(String wishlistId, NewWantedProduct newWantedProduct) throws Exception {
    return mockMvc.perform(post("/wanted/wishlist/{wishlistId}", wishlistId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newWantedProduct)));
  }

  private String generatedWantedProductId(String wishlistId, NewWantedProduct newWantedProduct) throws Exception {
    String response = createWantedProduct(wishlistId, newWantedProduct)
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(response, WantedProduct.class).getId();
  }

}
