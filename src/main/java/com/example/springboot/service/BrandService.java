package com.example.springboot.service;

import com.example.springboot.exception.BrandNotFoundException;
import com.example.springboot.exception.CarNotFoundException;
import com.example.springboot.model.Brand;
import com.example.springboot.repository.BrandRepository;
import com.example.springboot.exception.BrandAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand addBrand(Brand brand) {
        Optional<Brand> brandOptional = brandRepository.findByBrandNameIgnoreCase(brand.getBrandName());

        if (brandOptional.isPresent()) {
            log.info("Brand '{}' already exists", brand.getBrandName());
            throw new BrandAlreadyExistException("Brand already exists: " + brand.getBrandName());
        }

        try {
            log.info("Adding brand: {}", brand);
            Brand savedBrand = brandRepository.save(brand);
            log.info("Added brand: {}", savedBrand);
            return savedBrand;
        } catch (Exception error) {
            log.error("Error while adding brand: {}", brand);
            throw new RuntimeException("Error while saving brand", error);
        }
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Car not found with ID : {}.Can not delete.", id);
                    return new BrandNotFoundException("Car not found with ID: " + id);
                });
        brandRepository.delete(brand);
        log.info("Deleted brand: {}", brand);
    }

    public Brand updateBrand(Long id, Brand editedBrand) {
        Brand existBrand = brandRepository.findById(id)
                .orElseThrow(() ->{
                    log.error("Car not found with ID : {}.Can not update.", id);
                    return new BrandNotFoundException("Brand not found with ID: " + id);
                });
        existBrand.setBrandName(editedBrand.getBrandName());
        log.info("Updating brand: {}", existBrand);

        try {
            Brand updatedBrand = brandRepository.save(existBrand);
            log.info("Updated brand: {}", updatedBrand);
            return updatedBrand;
        } catch (Exception e) {
            log.error("Error while updating brand: {}", existBrand);
            throw new RuntimeException(e);
        }

    }

}
