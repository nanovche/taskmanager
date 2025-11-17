package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;

public class UserCreationFailedException extends BaseAppException{

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ApiErrorCode getErrorCode() {
        return ApiErrorCode.USER_CREATION_FAILED;
    }

    public UserCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserCreationFailedException(Throwable cause) {
        super(cause);
    }
}