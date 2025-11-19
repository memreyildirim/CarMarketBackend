package com.example.springboot.repository;

import com.example.springboot.model.Car;
import com.example.springboot.model.Favorite;
import com.example.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserAndCar(User user, Car car);
    void deleteByUser_IdAndCar_CarId(Long userId, Long carId);
    List<Favorite> findByUser(User user);
}


