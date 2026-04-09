package com.ordermanagement.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, length=10)
    private String mobileNumber;

    @Column(nullable=false)
    private Long productId;

    @Column(nullable=false)
    private int quantity;

    private double total;

    private double gstOnTotal;

    private double totalAmount;

    public Orders() {
    }

    public Orders(Long id, String name, String mobileNumber, Long productId, int quantity, double total, double gstOnTotal, double totalAmount) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.gstOnTotal = gstOnTotal;
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getGstOnTotal() {
        return this.gstOnTotal;
    }

    public void setGstOnTotal(double gstOnTotal) {
        this.gstOnTotal = gstOnTotal;
    }
}
