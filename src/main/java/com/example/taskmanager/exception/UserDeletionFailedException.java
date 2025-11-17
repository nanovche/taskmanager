package com.example.taskmanager.exception;

public class UserDeletionFailedException extends BaseAppException{
    public UserDeletionFailedException(String message, Throwable cause) {
    super(message, cause);
    }
}