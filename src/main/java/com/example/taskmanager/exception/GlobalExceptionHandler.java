package com.example.taskmanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
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

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Map<String, String>> handleUserCreation(UserCreationException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.USER_CREATION_FAILED.name());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchUser(NoSuchUserException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("error", ApiErrorCode.NO_SUCH_USER.name());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
