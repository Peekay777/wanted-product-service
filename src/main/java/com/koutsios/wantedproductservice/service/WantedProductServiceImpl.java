package com.koutsios.wantedproductservice.service;

import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;
import com.koutsios.wantedproductservice.repository.WantedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WantedProductServiceImpl implements WantedProductService {

  @Autowired
  private WantedProductRepository repository;

  @Override
  public WantedProduct createWantedProduct(String wishlistId, NewWantedProduct newWantedProduct) {
    WantedProduct wantedProduct = WantedProduct.builder()
        .wishlistId(wishlistId)
        .name(newWantedProduct.getName())
        .productDetailsLink(newWantedProduct.getProductDetailsLink())
        .status(newWantedProduct.getStatus())
        .build();
    return repository.save(wantedProduct);
  }

}
