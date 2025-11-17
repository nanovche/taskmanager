package com.example.taskmanager.exception;


import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseAppException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public ApiErrorCode getErrorCode() {
        return ApiErrorCode.USER_NOT_FOUND;
    }

    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}