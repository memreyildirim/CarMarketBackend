package com.example.springboot.exception;

public class EmailALreadyExistException extends RuntimeException {
    public EmailALreadyExistException(String message) {
        super(message);
    }
}
