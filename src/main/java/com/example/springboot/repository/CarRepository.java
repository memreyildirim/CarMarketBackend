package com.example.springboot.repository;

import com.example.springboot.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    //brand a göre filtreleme yapmak için kullanacağım
    List<Car> findByBrand_BrandName(String brandName);
}
