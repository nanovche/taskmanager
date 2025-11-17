package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;

public class UserDeletionFailedException extends BaseAppException{
    public UserDeletionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        if (getCause() instanceof UserNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (getCause() instanceof RepositoryException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public ApiErrorCode getErrorCode() {
        if (getCause() instanceof UserNotFoundException) {
            return ApiErrorCode.USER_NOT_FOUND;
        } else if (getCause() instanceof RepositoryException) {
            return ApiErrorCode.INTERNAL_SERVER_ERROR;
        } else {
            return ApiErrorCode.INTERNAL_SERVER_ERROR;
        }
    }
}