package com.example.taskmanager.exception.handlers;

import com.example.taskmanager.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.taskmanager.exception.ApiErrorCode.UNKNOWN_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.USER_ALREADY_EXISTS.name());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(UserCreationFailedException.class)
    public ResponseEntity<Map<String, String>> handleUserCreationException(UserCreationFailedException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.USER_CREATION_FAILED.name());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler({
            UserFetchingFailedException.class,
            UserDeletionFailedException.class
    })
    public ResponseEntity<Map<String, String>> handleUserOperationException(RuntimeException ex, HttpServletRequest request) {

        Throwable cause = ex.getCause();
        Map<String, String> body = prepareCommonBodyData(cause, request);
        if (cause instanceof UserNotFoundException) {
            body.put("error", ApiErrorCode.NO_SUCH_USER.name());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        } else if (cause instanceof RepositoryException) {
            body.put("error", ApiErrorCode.INTERNAL_SERVER_ERROR.name());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", UNKNOWN_ERROR.name()
            ));
        }
    }

    private Map<String, String> prepareCommonBodyData(Throwable ex, HttpServletRequest request){

        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return body;
    }
}