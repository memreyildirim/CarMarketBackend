package com.example.springboot;

import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("emre").isEmpty()) {
                User user = new User();
                user.setUsername("emre");
                user.setPassword(passwordEncoder.encode("emre123"));
                user.setRole("USER");
                userRepository.save(user);
            }
        };
    }

}
