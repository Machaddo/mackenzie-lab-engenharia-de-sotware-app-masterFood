package com.example.demo.auth.exceptions;

public class UnmatchingUserCredentialsException extends RuntimeException {

    private String message;

    public UnmatchingUserCredentialsException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
