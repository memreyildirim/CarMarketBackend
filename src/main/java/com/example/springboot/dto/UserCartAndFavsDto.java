package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCartAndFavsDto {
    private AdminUserDto adminUserDto;
    private CartDto cartDto;
    private List<CarDto> favorites;
}
