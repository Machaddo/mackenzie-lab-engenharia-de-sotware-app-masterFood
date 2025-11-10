package com.example.demo.model.exceptions;

public class LoginAlreadyExistsException extends RuntimeException {

    private String message;

    public LoginAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
