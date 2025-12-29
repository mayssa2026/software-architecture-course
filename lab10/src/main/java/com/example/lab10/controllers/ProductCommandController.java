package com.example.lab10.controllers;

import com.example.lab10.dtos.ProductRequest;
import jakarta.validation.Valid;
import com.example.lab10.model.ProductCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.lab10.services.ProductCommandService;

@RestController
@RequestMapping("/api/commands/products")
public class ProductCommandController {
    private final ProductCommandService service;

    public ProductCommandController(ProductCommandService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCommand create(@RequestBody @Valid ProductRequest request) {
        return service.createOrUpdate(
                request.getProductNumber(),
                request.getName(),
                request.getPrice()
        );
    }

    @PutMapping("/{productNumber}")
    public ProductCommand update(@PathVariable String productNumber,
                                 @RequestBody @Valid ProductRequest request) {
        return service.createOrUpdate(
                productNumber,
                request.getName(),
                request.getPrice()
        );
    }

    @DeleteMapping("/{productNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String productNumber) {
        service.delete(productNumber);
    }
}
