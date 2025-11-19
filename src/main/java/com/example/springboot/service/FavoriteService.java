package com.example.springboot.service;

import com.example.springboot.mapper.CarMapper;
import com.example.springboot.dto.CarDto;
import com.example.springboot.model.Car;
import com.example.springboot.model.Favorite;
import com.example.springboot.model.User;
import com.example.springboot.repository.CarRepository;
import com.example.springboot.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final CarRepository carRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, CarRepository carRepository) {
        this.favoriteRepository = favoriteRepository;
        this.carRepository = carRepository;
    }

    @Transactional
    public void addFavorite(User user, Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found: " + carId));
        // duplicate check opsiyonel
        if (!favoriteRepository.existsByUserAndCar(user, car)) {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setCar(car);
            favoriteRepository.save(favorite);
        }
    }

    @Transactional
    public void removeFavorite(User user, Long carId) {
        favoriteRepository.deleteByUser_IdAndCar_CarId(user.getId(), carId);
    }

    @Transactional()
    public List<CarDto> getUserFavoriteCars(User user) {
        return favoriteRepository.findByUser(user)
                .stream()
                .map(fav -> CarMapper.toCarDto(fav.getCar()))
                .collect(Collectors.toList());
    }
}


