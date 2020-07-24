package com.koutsios.wantedproductservice.repository;

import com.koutsios.wantedproductservice.domain.WantedProduct;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WantedProductRepository extends MongoRepository<WantedProduct, String> {

  List<WantedProduct> findByWishlistId(String wishlistId);

}
