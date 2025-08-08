package com.example.springboot.controller;

import com.example.springboot.dto.CarDto;
import com.example.springboot.mapper.CarMapper;
import com.example.springboot.model.Brand;
import com.example.springboot.model.Car;
import com.example.springboot.model.Photo;
import com.example.springboot.repository.BrandRepository;
import com.example.springboot.repository.CarRepository;
import com.example.springboot.service.CarService;
import com.example.springboot.service.PhotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4160")
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {


    @Autowired
    private CarService carService;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private BrandRepository brandRepository;

    /*get all cars
    @GetMapping
    public List<Car> getAllCars() {
       return carService.getAllCars();
    }*/


    //carDto kullanarak response da sürekli iç iç e car photo döngüsünü kırdık
    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCarss() {
        List<CarDto> carDtos = carService.getAllCarss();
        return ResponseEntity.ok(carDtos);
    }

    //filtering ekleyeceğimde bu endpoint kullanılacak
    @GetMapping("/filter")
    public Page<Car> getPagedCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "model") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc
    ) {
        return carService.getCars(page, size, sortBy, asc);
    }

    //asıl çalışan fotosuz
    /*
    //add car rest api
    @PostMapping
    public Car addCar(@RequestBody Car car){
        return carService.saveCar(car);
    }
     */

    //2 olabilecek
    /*
    @PostMapping("/api/photos")
    public ResponseEntity<?> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("carId") Long carId
    ) throws IOException {
        Photo saved = photoService.savePhoto(file, carId);
        return ResponseEntity.ok(saved);
    }

     */

    @PostMapping
    public ResponseEntity<?> createCarWithPhoto(
            @RequestParam("brandId") Long brandId,
            @RequestParam("model") String model,
            @RequestParam("carSpecification") String carSpecification,
            @RequestParam("engineVolume") Float engineVolume,
            @RequestParam("isNew") Boolean isNew,
            @RequestParam("price") BigDecimal price,
            @RequestParam("releaseDatetime") LocalDateTime releaseDatetime,
            @RequestParam("photo") MultipartFile photo
    ) {
        Brand brand = brandRepository.findById(brandId).orElseThrow();
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setCarSpecification(carSpecification);
        car.setEngineVolume(engineVolume);
        car.setIsNew(isNew);
        car.setPrice(price);
        car.setReleaseDatetime(releaseDatetime);

        car = carService.saveCar(car); // 👈 Önce araba kayıt

        if (photo != null && !photo.isEmpty()) {
            try {
                photoService.savePhoto(photo, car.getCarId()); // 👈 Fotoğrafı araba ile ilişkilendir
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return ResponseEntity.ok().build();
    }






    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        Car car = carService.findCarById(id);
        CarDto dto = CarMapper.toCarDto(car);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @RequestBody CarDto updatedCarData){
        CarDto updatedDto = carService.updateCar(id, updatedCarData);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }




}
