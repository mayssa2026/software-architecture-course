package com.example.lab10.controllers;


import com.example.lab10.model.ProductQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.lab10.services.ProductQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/query/products")
public class ProductQueryController {
    private final ProductQueryService service;

    public ProductQueryController(ProductQueryService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductQuery> getAll() {
        return service.getAll();
    }

    @GetMapping("/{productNumber}")
    public ProductQuery getOne(@PathVariable String productNumber) {
        return service.getByProductNumber(productNumber);
    }
}
