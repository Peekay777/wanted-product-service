package com.koutsios.wantedproductservice.service;

import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;

public interface WantedProductService {

  WantedProduct createWantedProduct(String wishlistId, NewWantedProduct newWantedProduct);

}
