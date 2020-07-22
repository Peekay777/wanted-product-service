package com.koutsios.wantedproductservice.repository;

import com.koutsios.wantedproductservice.domain.WantedProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WantedProductRepository extends MongoRepository<WantedProduct, String> {
}
