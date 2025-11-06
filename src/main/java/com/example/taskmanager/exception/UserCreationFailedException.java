package com.example.taskmanager.exception;

public class UserCreationFailedException extends RuntimeException{
    public UserCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}