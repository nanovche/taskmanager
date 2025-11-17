package com.example.taskmanager.exception;


public class UserAlreadyExistsException extends BaseAppException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}