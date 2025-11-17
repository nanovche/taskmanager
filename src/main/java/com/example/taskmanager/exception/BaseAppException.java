package com.example.taskmanager.exception;

public class BaseAppException extends RuntimeException{

    public BaseAppException(String message) {
        super(message);
    }

    public BaseAppException(Throwable cause) {
        super(cause);
    }

    public BaseAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
