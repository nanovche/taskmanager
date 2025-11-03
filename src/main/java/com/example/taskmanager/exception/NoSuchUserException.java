package com.example.taskmanager.exception;


public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }
}