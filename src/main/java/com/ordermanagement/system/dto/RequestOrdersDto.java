package com.ordermanagement.system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RequestOrdersDto {

    @NotBlank(message="Name is required")
    private String name;

    @NotBlank(message="Mobile number is required")
    @Size(min=10, max=10, message="Mobile Number Must be 10 Digits")
    private String mobileNumber;

    @NotNull(message="Product Id is required")
    private Long productId;

    @NotNull(message="Quantity is required")
    @Min(value=1L, message="Quantity must be at least 1")
    private Integer quantity;

    public String getName() {
        return this.name;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }
}
