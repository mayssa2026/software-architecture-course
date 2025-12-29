package com.example.lab10.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("product_events")
public class ProductEvent {
    @Id
    private String id;

    private String productNumber;
    private String type; // CREATED, UPDATED, DELETED
    private String name;
    private Double price;
    private Instant timestamp = Instant.now();

    public ProductEvent() {
    }

    public ProductEvent(String productNumber, String type, String name, Double price) {
        this.productNumber = productNumber;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}