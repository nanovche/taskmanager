package com.example.taskmanager.exception;

public class UserDeletionFailedException extends UserOperationFailedException{
    public UserDeletionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}