package com.example.springboot.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long carId;
    private String brandName;
    private String model;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal linePrice;
    private CarDto car;
}
