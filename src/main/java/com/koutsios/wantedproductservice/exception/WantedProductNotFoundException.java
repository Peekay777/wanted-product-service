package com.koutsios.wantedproductservice.exception;

public class WantedProductNotFoundException extends RuntimeException {

  public WantedProductNotFoundException(String wantedProductId) {
    super("Could find Wishlist " + wantedProductId);
  }

}
