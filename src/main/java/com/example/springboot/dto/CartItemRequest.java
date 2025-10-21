package com.example.springboot.dto;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long carId;
    private Integer quantity;
}
