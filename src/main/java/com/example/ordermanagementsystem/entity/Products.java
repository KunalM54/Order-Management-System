package com.example.ordermanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Products {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private int price;

    private int stock;

    private int threshHold;

    public Products() {
    }

    public Products(Long id, String productName, int price, int stock, int threshHold) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.threshHold = threshHold;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getThreshHold() {
        return this.threshHold;
    }

    public void setThreshHold(int threshHold) {
        this.threshHold = threshHold;
    }
}
