package com.example.demo.model.exceptions;

public class InvalidApiResponseException extends RuntimeException{
    
    private String message;

    
    public InvalidApiResponseException(String message){
        super(message);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
