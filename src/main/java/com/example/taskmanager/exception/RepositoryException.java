package com.example.taskmanager.exception;


import org.springframework.http.HttpStatus;

public class RepositoryException extends BaseAppException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ApiErrorCode getErrorCode() {
        return ApiErrorCode.INTERNAL_SERVER_ERROR;
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}