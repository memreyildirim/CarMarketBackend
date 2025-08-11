package com.example.springboot.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4160")
@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping
    public ResponseEntity<String> secureEndpoint() {
        return ResponseEntity.ok("token verified,access succesful");
    }
}
