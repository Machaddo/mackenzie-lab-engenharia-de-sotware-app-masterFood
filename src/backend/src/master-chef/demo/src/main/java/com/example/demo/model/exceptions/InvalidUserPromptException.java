package com.example.demo.model.exceptions;

public class InvalidUserPromptException extends RuntimeException{
    
    private String message;

    
    public InvalidUserPromptException(String message){
        super(message);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
