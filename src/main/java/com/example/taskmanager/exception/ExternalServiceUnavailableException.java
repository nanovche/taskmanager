package com.example.taskmanager.exception;

public class ExternalServiceUnavailableException extends RuntimeException{

    public ExternalServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalServiceUnavailableException(Throwable cause) {
        super(cause);
    }
}
