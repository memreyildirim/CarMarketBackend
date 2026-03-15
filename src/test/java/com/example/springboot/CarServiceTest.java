//package com.example.springboot;
//
//import com.example.springboot.model.Brand;
//import com.example.springboot.model.Car;
//import com.example.springboot.repository.BrandRepository;
//import com.example.springboot.repository.CarRepository;
//import com.example.springboot.service.CarService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//
//@ExtendWith(MockitoExtension.class)
//public class CarServiceTest {
//
//        @Mock
//        private CarRepository carRepository;
//
//        @InjectMocks
//        private CarService carService;
//
//        @Test
//        void shouldAddCarSuccessfully() {
//
//            Brand brand = new Brand(null, "Mercedes");
//            Car carToSave = new Car(10L, brand, "C180","c class", 1.6f, true,
//                    new BigDecimal("2200000"),LocalDateTime.of(2025,7, 3, 16, 36));
//
//            Car expectedCar = new Car(10L, brand, "C180","c class", 1.6f, true,
//                    new BigDecimal("2200000"),LocalDateTime.of(2025,7, 3, 16, 36));
//
//
//            Mockito.when(carRepository.save(Mockito.any(Car.class))).thenReturn(expectedCar);
//
//            Car result = carService.saveCar(carToSave);
//
//            Assertions.assertNotNull(result.getCarId());
//            Assertions.assertEquals("C180", result.getModel());
//            Assertions.assertEquals("Mercedes", result.getBrand().getBrandName());
//        }
//
//    @Test
//    void shouldReturnListOfCars() {
//
//        Brand brand = new Brand(1L, "Mercedes");
//        Car car1 = new Car(10L, brand, "C180","c class", 1.6f, true,
//                new BigDecimal("2200000"),LocalDateTime.of(2025,7, 3, 16, 36));
//        Car car2 = new Car(101L, brand, "C280","c class", 2.0f, true,
//                new BigDecimal("2200000"),LocalDateTime.of(2023,7, 5, 16, 36));
//
//        List<Car> mockCars = Arrays.asList(car1, car2);
//
//        Mockito.when(carRepository.findAll()).thenReturn(mockCars);
//
//
//        List<Car> result = carService.getAllCars();
//
//
//        Assertions.assertEquals(2, result.size());
//        Assertions.assertEquals("c class", result.get(0).getCarSpecification());
//        Assertions.assertEquals("Mercedes", result.get(0).getBrand().getBrandName());
//    }
//
//    @Test
//    void shouldReturnCarById() {
//        // Arrange
//        Brand brand = new Brand(1L, "Mercedes");
//        Car car = new Car(18L, brand, "C180","c class", 1.6f, true,
//                new BigDecimal("2200000"),LocalDateTime.of(2025,7, 3, 16, 36));
//
//        Mockito.when(carRepository.findById(18L)).thenReturn(Optional.of(car));
//
//        // Act
//        Car result = carService.findCarById(18L);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals("c class", result.getCarSpecification());
//        Assertions.assertEquals("Mercedes", result.getBrand().getBrandName());
//    }
//
//    @Test
//    void shouldDeleteCarById() {
//        // Arrange
//        Brand brand = new Brand(1L, "Mercedes");
//        Car car = new Car(18L, brand, "C180","c class", 1.6f, true,
//                new BigDecimal("2200000"),LocalDateTime.of(2025,7, 3, 16, 36));
//
//        Mockito.when(carRepository.findById(18L)).thenReturn(Optional.of(car));
//        Mockito.doNothing().when(carRepository).delete(car);
//
//        carService.deleteCar(18L);
//
//        Mockito.verify(carRepository).delete(car);
//    }
//
//
//
//
//
//
//
//
//}
