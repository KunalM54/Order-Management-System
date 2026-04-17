package com.example.ordermanagementsystem.dto;

public class BillDetails {
    private double total;
    private double gst;
    private double finalAmount;

    public BillDetails(double total, double gst, double finalAmount) {
        this.total = total;
        this.gst = gst;
        this.finalAmount = finalAmount;
    }

    public double getTotal() {
        return this.total;
    }

    public double getGst() {
        return this.gst;
    }

    public double getFinalAmount() {
        return this.finalAmount;
    }
}
