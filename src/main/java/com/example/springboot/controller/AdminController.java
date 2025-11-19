package com.example.springboot.controller;

import com.example.springboot.dto.AdminUserDto;
import com.example.springboot.dto.CarDto;
import com.example.springboot.dto.CartDto;
import com.example.springboot.dto.UserCartAndFavsDto;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.CarService;
import com.example.springboot.service.CartService;
import com.example.springboot.service.FavoriteService;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4160","https://sortably-nonaffiliating-my.ngrok-free.dev"})
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserRepository userRepository;

    private User currentUser(UserDetails principal){
        return userService.findByEmail(principal.getUsername());
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AdminUserDto>> getAllUsers(@AuthenticationPrincipal UserDetails principal){
        List<AdminUserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}/details")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserCartAndFavsDto> getUserDetails(@AuthenticationPrincipal UserDetails principal,@PathVariable Long userId){

        User targetUser = userService.findById(userId);

        AdminUserDto userDto = new AdminUserDto(
                targetUser.getId(),
                targetUser.getUsername(),
                targetUser.getEmail(),
                targetUser.getRole()
        );

        CartDto cart = cartService.getCart(targetUser);
        List<CarDto> favorites = favoriteService.getUserFavoriteCars(targetUser);

        UserCartAndFavsDto response = new UserCartAndFavsDto(userDto,cart,favorites);

        return ResponseEntity.ok(response);


    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
