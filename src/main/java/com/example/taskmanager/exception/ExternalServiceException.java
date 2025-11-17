package com.example.taskmanager.exception;

public class ExternalServiceException extends BaseAppException {
    public ExternalServiceException(Throwable cause) {
        super(cause);
    }

    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}