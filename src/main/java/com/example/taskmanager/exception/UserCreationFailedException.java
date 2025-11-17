package com.example.taskmanager.exception;

public class UserCreationFailedException extends BaseAppException{
    public UserCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserCreationFailedException(Throwable cause) {
        super(cause);
    }
}