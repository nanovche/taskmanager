package com.example.taskmanager.exception;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(Throwable cause) {
        super(cause);
    }

    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}