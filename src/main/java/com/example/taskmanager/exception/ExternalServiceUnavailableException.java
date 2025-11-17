package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;

public class ExternalServiceUnavailableException extends BaseAppException{

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_GATEWAY;
    }

    @Override
    public ApiErrorCode getErrorCode() {
        return ApiErrorCode.BAD_GATEWAY;
    }

    public ExternalServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
        setPublicMessage("The service is temporarily unavailable. Please try again later.");
    }
}