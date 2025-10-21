package com.example.springboot.service;

import com.example.springboot.dto.CarDto;
import com.example.springboot.dto.CartDto;
import com.example.springboot.dto.CartItemDto;
import com.example.springboot.dto.CartItemRequest;
import com.example.springboot.mapper.CarMapper;
import com.example.springboot.model.Car;
import com.example.springboot.model.Cart;
import com.example.springboot.model.CartItem;
import com.example.springboot.model.User;
import com.example.springboot.repository.CarRepository;
import com.example.springboot.repository.CartItemRepository;
import com.example.springboot.repository.CartRepository;
import com.example.springboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired private CarRepository carRepository;

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUser(user);
                    return cartRepository.save(c);
                });
    }

    public CartDto getCart(User user) {
        Cart cart = getOrCreateCart(user);
        return toDto(cart);
    }

    @Transactional
    public CartDto addItem(User user, CartItemRequest req) {
        Cart cart = getOrCreateCart(user);
        Car car = carRepository.findById(req.getCarId()).orElseThrow();

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getCar().getCarId().equals(car.getCarId()))
                .findFirst().orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setCar(car);
            item.setQuantity(Math.max(req.getQuantity(), 1));
            item.setUnitPrice(car.getPrice());
            cart.getItems().add(item);
        } else {
            int newQuantity = item.getQuantity() + Math.max(req.getQuantity(), 1);
            item.setQuantity(newQuantity);
        }
        cartRepository.save(cart);
        return toDto(cart);
    }

    @Transactional
    public CartDto updateQuantity(User user, Long carId, Integer quantity) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().stream()
                .filter(i -> i.getCar().getCarId().equals(carId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(Math.max(quantity, 1)));
        cartRepository.save(cart);
        return toDto(cart);
    }

    @Transactional
    public CartDto removeItem(User user, Long carId) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().removeIf(i -> i.getCar().getCarId().equals(carId));
        cartRepository.save(cart);
        return toDto(cart);
    }

    @Transactional
    public void clear(User user) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private CartDto toDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setItems(cart.getItems().stream().map(i -> {
            CartItemDto d = new CartItemDto();
            d.setCarId(i.getCar().getCarId());
            d.setBrandName(i.getCar().getBrand().getBrandName());
            d.setModel(i.getCar().getModel());
            d.setQuantity(i.getQuantity());
            d.setUnitPrice(i.getUnitPrice());
            d.setLinePrice(i.getLineTotal());

            CarDto carDto = CarMapper.toCarDto(i.getCar());
            d.setCar(carDto);

            return d;
        }).collect(Collectors.toList()));
        dto.setTotalPrice(cart.getTotalPrice());
        return dto;
    }
}
