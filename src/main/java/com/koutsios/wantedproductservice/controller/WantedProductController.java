package com.koutsios.wantedproductservice.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;
import com.koutsios.wantedproductservice.service.WantedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wanted")
public class WantedProductController {

  @Autowired
  private WantedProductService wantedProductService;

  @PostMapping("/{wishlistId}")
  @ResponseStatus(CREATED)
  public WantedProduct createWantedProduct(@PathVariable String wishlistId,
                                           @RequestBody NewWantedProduct newWantedProduct) {
    return wantedProductService.createWantedProduct(wishlistId, newWantedProduct);
  }

}
