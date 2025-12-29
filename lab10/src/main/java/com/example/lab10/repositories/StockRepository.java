package com.example.lab10.repositories;

import com.example.lab10.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StockRepository extends MongoRepository<Stock, String> {
    Optional<Stock> findByProductNumber(String productNumber);
    void deleteByProductNumber(String productNumber);
}