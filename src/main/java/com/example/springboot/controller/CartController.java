// src/main/java/com/example/springboot/controller/CartController.java
package com.example.springboot.controller;

import com.example.springboot.dto.CartDto;
import com.example.springboot.dto.CartItemRequest;
import com.example.springboot.model.User;
import com.example.springboot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4160","https://sortably-nonaffiliating-my.ngrok-free.dev"})
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired private CartService cartService;
    @Autowired private com.example.springboot.repository.UserRepository userRepository;

    private User currentUser(UserDetails principal) {
        return userRepository.findByUsername(principal.getUsername()).orElseThrow();
    }

    @GetMapping
    public CartDto getCart(@AuthenticationPrincipal UserDetails principal) {
        return cartService.getCart(currentUser(principal));
    }

    @PostMapping("/items")
    public CartDto addItem(@AuthenticationPrincipal UserDetails principal,
                           @RequestBody CartItemRequest request) {
        return cartService.addItem(currentUser(principal), request);
    }

    @PutMapping("/items/{carId}")
    public CartDto updateQty(@AuthenticationPrincipal UserDetails principal,
                             @PathVariable Long carId,
                             @RequestParam Integer quantity) {
        return cartService.updateQuantity(currentUser(principal), carId, quantity);
    }

    @DeleteMapping("/items/{carId}")
    public CartDto remove(@AuthenticationPrincipal UserDetails principal,
                          @PathVariable Long carId) {
        return cartService.removeItem(currentUser(principal), carId);
    }

    @DeleteMapping
    public void clear(@AuthenticationPrincipal UserDetails principal) {
        cartService.clear(currentUser(principal));
    }
}