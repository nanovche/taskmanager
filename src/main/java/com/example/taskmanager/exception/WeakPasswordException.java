package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;

public class WeakPasswordException extends BaseAppException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ApiErrorCode getErrorCode() {
        return ApiErrorCode.WEAK_PASSWORD;
    }

    public WeakPasswordException(String message) {
        super(message);
        setPublicMessage("Password does not meet security requirements.");
    }
}