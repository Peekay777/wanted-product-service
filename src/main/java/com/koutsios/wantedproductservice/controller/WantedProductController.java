package com.koutsios.wantedproductservice.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.koutsios.wantedproductservice.domain.WantedProduct;
import com.koutsios.wantedproductservice.dto.NewWantedProduct;
import com.koutsios.wantedproductservice.service.WantedProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping("/wishlist/{wishlistId}")
  @ResponseStatus(CREATED)
  public WantedProduct createWantedProduct(@PathVariable String wishlistId,
                                           @RequestBody NewWantedProduct newWantedProduct) {
    return wantedProductService.createWantedProduct(wishlistId, newWantedProduct);
  }

  @GetMapping("/{wantedProductId}")
  @ResponseStatus(OK)
  public WantedProduct getWantedProduct(@PathVariable String wantedProductId) {
    return wantedProductService.getWantedProduct(wantedProductId);
  }

  @GetMapping("/wishlist/{wishlistId}")
  @ResponseStatus(OK)
  public List<WantedProduct> getAllWantedProducts(@PathVariable String wishlistId) {
    return wantedProductService.getAllWantedProducts(wishlistId);
  }

}
