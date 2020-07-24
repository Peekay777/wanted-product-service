package com.koutsios.wantedproductservice.fixture;

import static com.koutsios.wantedproductservice.constant.ProductStatus.AVAILABLE;

import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;

public class WantedProductFixture {

  public static WantedProduct aWantedProduct(String id) {
    return WantedProduct.builder()
        .id(id)
        .wishlistId("wishlistId")
        .name("ProductName")
        .productDetailsLink("http://an.example.com/product")
        .status(AVAILABLE)
        .build();
  }

  public static NewWantedProduct aNewWantedProduct() {
    return NewWantedProduct.builder()
        .name("ProductName")
        .productDetailsLink("http://an.example.com/product")
        .status(AVAILABLE)
        .build();
  }
}
