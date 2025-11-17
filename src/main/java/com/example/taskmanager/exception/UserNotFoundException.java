package com.example.taskmanager.exception;


public class UserNotFoundException extends BaseAppException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}