package com.koutsios.wantedproductservice.service;

import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;
import java.util.List;

public interface WantedProductService {

  WantedProduct createWantedProduct(String wishlistId, NewWantedProduct newWantedProduct);

  WantedProduct getWantedProduct(String wantedProductId);

  List<WantedProduct> getAllWantedProducts(String wishlistId);
}
