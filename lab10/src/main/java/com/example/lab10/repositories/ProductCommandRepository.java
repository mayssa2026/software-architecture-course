package com.example.lab10.repositories;


import com.example.lab10.model.ProductCommand;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductCommandRepository extends MongoRepository<ProductCommand, String> {
    Optional<ProductCommand> findByProductNumber(String productNumber);
    void deleteByProductNumber(String productNumber);
}
