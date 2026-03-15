//package com.example.springboot;
//
//import com.example.springboot.controller.CarController;
//import com.example.springboot.model.Brand;
//import com.example.springboot.model.Car;
//import com.example.springboot.service.CarService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
////hatalı düzelt
//
//@WebMvcTest(CarController.class)
//public class CarControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private CarService carService;
//
//    @Test
//    void shouldReturnCarList() throws Exception {
//        Brand brand = new Brand(null, "Mercedes");
//        Car car = new Car(10L, brand, "C180","c class", 1.6f, true,
//                new BigDecimal("2200000"),LocalDateTime.of(2025,7, 3, 16, 36));
//
//        Mockito.when(carService.getAllCars()).thenReturn(List.of(car));
//
//        mockMvc.perform(get("/api/v1/cars"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].model").value("C180"))
//                .andExpect(jsonPath("$[0].brand.brandName").value("Mercedes"));
//    }
//
//
//}
