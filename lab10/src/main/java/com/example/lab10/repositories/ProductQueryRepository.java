package com.example.lab10.repositories;

import com.example.lab10.model.ProductQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductQueryRepository extends MongoRepository<ProductQuery, String> {
    Optional<ProductQuery> findByProductNumber(String productNumber);
    void deleteByProductNumber(String productNumber);
}
