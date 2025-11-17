package com.example.taskmanager.exception;

public class ExternalServiceUnavailableException extends BaseAppException{

    public ExternalServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalServiceUnavailableException(Throwable cause) {
        super(cause);
    }
}
