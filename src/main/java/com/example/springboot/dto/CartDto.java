package com.example.springboot.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;
}
