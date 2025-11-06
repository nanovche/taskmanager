package com.example.taskmanager.exception;

public class UserFetchingFailedException extends RuntimeException{
    public UserFetchingFailedException(String message, Throwable cause) {
    super(message, cause);
    }
}