package com.example.taskmanager.exception;

public class UserDeletionFailedException extends RuntimeException{
    public UserDeletionFailedException(String message, Throwable cause) {
    super(message, cause);
    }
}