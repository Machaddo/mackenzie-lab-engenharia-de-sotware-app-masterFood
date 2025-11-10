package com.example.demo.auth.exceptions;

public class InvalidAuthenticationTokenException extends RuntimeException{
 
    private String message;

    public InvalidAuthenticationTokenException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
