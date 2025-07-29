package com.example.springboot.dto;

import com.example.springboot.model.Brand;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CarDto {
    private Long carId;
    private Brand Brand;
    private String brandName;
    private String model;
    private String carSpecification;
    private Float engineVolume;
    private Boolean isNew;
    private BigDecimal price;
    private LocalDateTime releaseDatetime;
    private String filePath;
}
