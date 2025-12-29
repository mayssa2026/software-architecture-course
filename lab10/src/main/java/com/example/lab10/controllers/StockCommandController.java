package com.example.lab10.controllers;

import com.example.lab10.dtos.StockRequest;
import jakarta.validation.Valid;
import com.example.lab10.model.Stock;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.lab10.services.StockCommandService;

@RestController
@RequestMapping("/api/commands/stocks")
public class StockCommandController {
    private final StockCommandService service;

    public StockCommandController(StockCommandService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Stock create(@RequestBody @Valid StockRequest request) {
        return service.createOrUpdate(
                request.getProductNumber(),
                request.getQuantity()
        );
    }

    @PutMapping("/{productNumber}")
    public Stock update(@PathVariable String productNumber,
                        @RequestBody @Valid StockRequest request) {
        return service.createOrUpdate(
                productNumber,
                request.getQuantity()
        );
    }

    @DeleteMapping("/{productNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String productNumber) {
        service.delete(productNumber);
    }
}
