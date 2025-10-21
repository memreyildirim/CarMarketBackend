package com.example.springboot.service;

import com.example.springboot.dto.CarDto;
import com.example.springboot.exception.CarNotFoundException;
import com.example.springboot.exception.CarSaveExcepiton;
import com.example.springboot.mapper.CarMapper;
import com.example.springboot.model.Car;
import com.example.springboot.model.Photo;
import com.example.springboot.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    private CarRepository carRepository;

    public Page<Car> getCars(int page, int size, String sortBy, boolean asc) {
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return carRepository.findAll(pageable);
    }


    public List<Car> getAllCars(){
        try {
            logger.info("Fetching all cars from repository");
            List<Car> cars = carRepository.findAll();
            logger.debug("Fetched {} cars", cars.size());
            return cars;
        } catch (Exception e) {
            logger.error("Error while fetching cars");
            throw new RuntimeException(e);
        }
    }




    public Car saveCar(Car car){
        try {
            logger.debug("Car save request received: {}", car);
            Car savedCar = carRepository.save(car);
            logger.info("Car saved succesfully with ID: {}", savedCar.getCarId());
            return savedCar;
        } catch (DataIntegrityViolationException e) {
            logger.error("Car did not save correctly", e);
            throw new CarSaveExcepiton("Car save failed");
        } catch (Exception e) {
            logger.error("Unexpected error during savCar:",car,e);
            throw new CarSaveExcepiton("Car save failed with unexpected error");
        }
    }

    public Car findCarById(Long id){
        return carRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Car not found with ID: {}", id);
                    return new CarNotFoundException("Car not found with ID: " + id);
                });
    }

    public CarDto updateCar(Long id, CarDto updatedCarData){
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Car not found with ID : {}", id);
                    return new CarNotFoundException("Car not found with ID: " + id);
                        });

        // Mevcut ID'yi koru
        Long existingId = existingCar.getCarId();

        // DTO'dan yeni entity oluştur
        Car updatedCar = CarMapper.toEntity(updatedCarData);
        updatedCar.setCarId(existingId); // ID'yi koru

        Car savedCar = carRepository.save(updatedCar);
        return CarMapper.toCarDto(savedCar);
    }

    public void deleteCar(Long id){
        Car car = carRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Car not found with ID : {}.Can not delete.", id);
                    return new CarNotFoundException("Car not found with ID: " + id);
                });
        carRepository.delete(car);
        logger.info("Car with ID: {} was deleted", id);
    }

    public List<CarDto> getAllCarss(){
        try {
            logger.info("Fetching all cars from repository");
            List<Car> cars = carRepository.findAll();
            logger.debug("Fetched {} cars", cars.size());
            return cars
                    .stream()
                    .map(CarMapper::toCarDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while fetching cars");
            throw new RuntimeException(e);
        }
    }


}
