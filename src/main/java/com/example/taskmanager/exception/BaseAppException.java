package com.example.taskmanager.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public abstract class BaseAppException extends RuntimeException{

    @Getter
    @Setter
    private String publicMessage;

    public abstract HttpStatus getHttpStatus();
    public abstract ApiErrorCode getErrorCode();

    public BaseAppException() {
    }

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