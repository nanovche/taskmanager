package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;

public class ExternalServiceException extends BaseAppException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_GATEWAY;
    }

    @Override
    public ApiErrorCode getErrorCode() {
        return ApiErrorCode.BAD_GATEWAY;
    }

    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
        setPublicMessage("Unable to complete the request due to invalid input.");
    }
}