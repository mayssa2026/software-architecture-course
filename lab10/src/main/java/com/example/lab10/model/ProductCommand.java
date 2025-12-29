package com.example.lab10.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_commands")
public class ProductCommand {
    @Id
    private String id;
    private String productNumber;
    private String name;
    private double price;

    public ProductCommand() {}

    public ProductCommand(String productNumber, String name, double price) {
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }

    public String getProductNumber() { return productNumber; }

    public void setProductNumber(String productNumber) { this.productNumber = productNumber; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }
}
