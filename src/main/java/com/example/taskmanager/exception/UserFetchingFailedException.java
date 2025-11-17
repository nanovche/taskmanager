package com.example.taskmanager.exception;


public class UserFetchingFailedException extends UserOperationFailedException {

    public UserFetchingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}