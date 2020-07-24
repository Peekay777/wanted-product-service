package com.koutsios.wantedproductservice.service;

import static com.koutsios.wantedproductservice.fixture.WantedProductFixture.aNewWantedProduct;
import static com.koutsios.wantedproductservice.fixture.WantedProductFixture.aWantedProduct;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.exception.WantedProductNotFoundException;
import com.koutsios.wantedproductservice.repository.WantedProductRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DisplayName("Wanted Product Service method test")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class WantedProductServiceImplTest {

  @Autowired
  private WantedProductService wantedProductService;

  @MockBean
  private WantedProductRepository repository;

  @Test
  @DisplayName("createWantedProduct - Create new wishlist successfully")
  void createWantedProduct_success() {
    when(repository.save(any(WantedProduct.class))).thenReturn(aWantedProduct("generatedId"));

    WantedProduct actualWantedProduct = wantedProductService.createWantedProduct("wishlistId", aNewWantedProduct());

    assertNotNull(actualWantedProduct.getId());
    assertEquals("wishlistId", actualWantedProduct.getWishlistId());
    verify(repository).save(any(WantedProduct.class));
  }

  @Test
  @DisplayName("getWantedProduct - Retrieve wanted product successfully")
  void getWantedProduct_success() {
    WantedProduct expectedWantedProduct = aWantedProduct("generatedId");
    when(repository.findById(anyString())).thenReturn(of(expectedWantedProduct));

    WantedProduct actualWantedProduct = wantedProductService.getWantedProduct("generatedId");

    assertNotNull(actualWantedProduct);
    assertSame(expectedWantedProduct, actualWantedProduct);
    verify(repository).findById(anyString());
  }

  @Test
  @DisplayName("getWantedProduct - Invalid wanted product - WantedProductNotFoundException")
  void getWantedProduct_invalidWantedId() {
    when(repository.findById(anyString())).thenReturn(empty());

    assertThrows(WantedProductNotFoundException.class, () -> wantedProductService.getWantedProduct("generatedId"));

    verify(repository).findById(anyString());
  }

  @Test
  @DisplayName("getAllWantedProducts - Retrieve all wantedproducts for a wishlist")
  void getAllWantedProducts_success() {
    List<WantedProduct> wantedProducts = Arrays.asList(
        aWantedProduct("generatedId1"),
        aWantedProduct("generatedId2")
    );
    when(repository.findByWishlistId(anyString())).thenReturn(wantedProducts);

    List<WantedProduct> actualWantedProduct = wantedProductService.getAllWantedProducts("wishlistId");

    assertEquals(2, actualWantedProduct.size());
    verify(repository).findByWishlistId(anyString());
  }
}