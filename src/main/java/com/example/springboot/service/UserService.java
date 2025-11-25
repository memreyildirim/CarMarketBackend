package com.example.springboot.service;

import com.example.springboot.dto.AdminUserDto;
import com.example.springboot.dto.RegisterRequest;
import com.example.springboot.exception.UserNameAlreadyExistException;
import com.example.springboot.exception.UsernameNotFoundException;
import com.example.springboot.model.Cart;
import com.example.springboot.model.Favorite;
import com.example.springboot.model.User;
import com.example.springboot.repository.CartRepository;
import com.example.springboot.repository.FavoriteRepository;
import com.example.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    public User findByEmail(String email) {
        return userRepository.findByemail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with this email")) ;
    }

    public void registerUser(RegisterRequest request) {
        boolean emailExist = userRepository.findByemail(request.getEmail()).isPresent();
        if (emailExist) {
            throw new UserNameAlreadyExistException("This email  already taken from another one user");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole("USER");

        userRepository.save(user);
    }

    public List<AdminUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new AdminUserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : "+ userId));
    }

    public void deleteUser(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : "+ id));
        // Cart'ı user'a göre bul ve sil
        Optional<Cart> cart = cartRepository.findByUser(user);
        cart.ifPresent(cartRepository::delete);

        // Favorite'ları user'a göre bul ve sil
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        favoriteRepository.deleteAll(favorites);

        userRepository.deleteById(id);
    }

    public void registerAdmin(RegisterRequest adminRequest) {
        boolean emailExist = userRepository.findByemail(adminRequest.getEmail()).isPresent();
        if (emailExist) {
            throw new UserNameAlreadyExistException("This email already taken from another one admin");
        }

        User user = new User();
        user.setUsername(adminRequest.getUsername());
        user.setEmail(adminRequest.getEmail());
        user.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        user.setRole("ADMIN");

        userRepository.save(user);
    }


}
