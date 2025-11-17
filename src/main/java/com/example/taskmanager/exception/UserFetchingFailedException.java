package com.example.taskmanager.exception;

public class UserFetchingFailedException extends BaseAppException{
    public UserFetchingFailedException(String message, Throwable cause) {
    super(message, cause);
    }
}