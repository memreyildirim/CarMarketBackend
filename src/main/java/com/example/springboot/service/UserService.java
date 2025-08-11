package com.example.springboot.service;

import com.example.springboot.dto.RegisterRequest;
import com.example.springboot.exception.UserNameAlreadyExistException;
import com.example.springboot.exception.UsernameNotFoundException;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void registerUser(RegisterRequest request) {
        boolean usernameExist = userRepository.findByUsername(request.getUsername()).isPresent();
        boolean emailExist = userRepository.findByemail(request.getEmail()).isPresent();
        if (usernameExist && emailExist) {
            throw new UserNameAlreadyExistException("This username and email combination already taken from another one user");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole("USER");

        userRepository.save(user);
    }


}
