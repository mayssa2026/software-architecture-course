package com.example.lab10.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {
    @NotBlank
    private String productNumber;
    @NotBlank
    private String name;
    @NotNull
    @Min(0)
    private Double price;

    public String getProductNumber() { return productNumber; }
    public void setProductNumber(String productNumber) { this.productNumber = productNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
