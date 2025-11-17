package com.example.taskmanager.exception.handlers;

import com.example.taskmanager.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.Map;

import static com.example.taskmanager.exception.ApiErrorCode.UNKNOWN_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorApiResponse> handleValidationException(ValidationException ex, HttpServletRequest request) {
        ErrorApiResponse body = prepareCommonResponseData(ex, request);
        body.setError(ApiErrorCode.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorApiResponse> handleUserExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        ErrorApiResponse body = prepareCommonResponseData(ex, request);
        body.setError(ApiErrorCode.USER_ALREADY_EXISTS.name());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(UserCreationFailedException.class)
    public ResponseEntity<ErrorApiResponse> handleUserCreationException(UserCreationFailedException ex, HttpServletRequest request) {
        ErrorApiResponse body = prepareCommonResponseData(ex,request);
        body.setError(ApiErrorCode.USER_CREATION_FAILED.name());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler({
            UserFetchingFailedException.class,
            UserDeletionFailedException.class
    })
    public ResponseEntity<ErrorApiResponse> handleUserOperationException(RuntimeException ex, HttpServletRequest request) {

        Throwable cause = ex.getCause();
        ErrorApiResponse body = prepareCommonResponseData(cause, request);
        if (cause instanceof UserNotFoundException) {
            body.setError(ApiErrorCode.USER_NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        } else if (cause instanceof RepositoryException) {
            body.setError(ApiErrorCode.INTERNAL_SERVER_ERROR.name());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        } else {
            body.setError(UNKNOWN_ERROR.name());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }

    @ExceptionHandler({
            ExternalCommunicationException.class,
            ExternalServiceUnavailableException.class
    })
    public ResponseEntity<ErrorApiResponse> handleExternalServiceServerException(ValidationException ex, HttpServletRequest request) {
        ErrorApiResponse errorApiResponse = prepareCommonResponseData(ex, request);
        errorApiResponse.setError(ApiErrorCode.BAD_GATEWAY.name());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorApiResponse);
    }

    @ExceptionHandler({
            ExternalServiceException.class,
    })
    public ResponseEntity<ErrorApiResponse> handleExternalServiceClientException(ValidationException ex, HttpServletRequest request) {
        ErrorApiResponse errorApiResponse = prepareCommonResponseData(ex, request);
        errorApiResponse.setError(ApiErrorCode.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorApiResponse);
    }

    private ErrorApiResponse prepareCommonResponseData(Throwable ex, HttpServletRequest request){

        return new ErrorApiResponse(
                ex.getMessage(),
                request.getRequestURI(),
                new Date().toString());
    }
}