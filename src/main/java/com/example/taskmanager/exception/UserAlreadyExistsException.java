package com.example.taskmanager.exception;


import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseAppException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public ApiErrorCode getErrorCode() {
        return ApiErrorCode.USER_ALREADY_EXISTS;
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}