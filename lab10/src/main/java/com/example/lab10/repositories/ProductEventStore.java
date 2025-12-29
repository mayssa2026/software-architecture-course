package com.example.lab10.repositories;

import com.example.lab10.model.ProductEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductEventStore extends MongoRepository<ProductEvent, String> {
    List<ProductEvent> findByProductNumberOrderByTimestampAsc(String productNumber);
}
