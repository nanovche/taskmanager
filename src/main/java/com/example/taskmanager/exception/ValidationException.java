package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends BaseAppException{

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ApiErrorCode getErrorCode() {
        return ApiErrorCode.BAD_REQUEST;
    }

    public ValidationException(String message) {
        super(message);
    }
}