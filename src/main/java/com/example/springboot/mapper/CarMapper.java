package com.example.springboot.mapper;

import com.example.springboot.dto.CarDto;
import com.example.springboot.model.Brand;
import com.example.springboot.model.Car;

public class CarMapper {
    public static CarDto toCarDto(Car car) {
        CarDto dto = new CarDto();
        dto.setCarId(car.getCarId());

        dto.setBrandName(car.getBrand().getBrandName());
        dto.setModel(car.getModel());
        dto.setCarSpecification(car.getCarSpecification());
        dto.setEngineVolume(car.getEngineVolume());
        dto.setIsNew(car.getIsNew());
        dto.setPrice(car.getPrice());
        dto.setReleaseDatetime(car.getReleaseDatetime());
        dto.setModel(car.getModel());
        dto.setPrice(car.getPrice());
        dto.setFilePath(car.getPhoto() != null ? car.getPhoto().getFilePath() : null);


        return dto;
    }

    public static Car toEntity(CarDto dto){
        Car car = new Car();
        car.setModel(dto.getModel());
        car.setPrice(dto.getPrice());
        car.setIsNew(dto.getIsNew());
        car.setReleaseDatetime(dto.getReleaseDatetime());
        car.setEngineVolume(dto.getEngineVolume());
        car.setCarSpecification(dto.getCarSpecification());
        car.setBrand(new Brand()); // dikkat!
        return car;
    }

}
