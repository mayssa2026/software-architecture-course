package com.example.lab10.services;

import com.example.lab10.model.ProductQuery;
import org.springframework.stereotype.Service;
import com.example.lab10.repositories.ProductQueryRepository;

import java.util.List;

@Service
public class ProductQueryService {
    private final ProductQueryRepository repo;

    public ProductQueryService(ProductQueryRepository repo) {
        this.repo = repo;
    }

    public List<ProductQuery> getAll() {
        return repo.findAll();
    }

    public ProductQuery getByProductNumber(String productNumber) {
        return repo.findByProductNumber(productNumber)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
