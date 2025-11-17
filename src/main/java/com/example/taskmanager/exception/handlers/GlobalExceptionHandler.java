package com.example.taskmanager.exception.handlers;

import com.example.taskmanager.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseAppException.class)
    public ResponseEntity<ErrorApiResponse> handleBaseAppException(BaseAppException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getHttpStatus()).body(buildApiErrorResponseData(ex, request));
    }

    private ErrorApiResponse buildApiErrorResponseData(BaseAppException ex, HttpServletRequest request){
        return new ErrorApiResponse(
                ex.getErrorCode().name(),
                ex.getPublicMessage(),
                request.getRequestURI(),
                new Date().toString());
    }
}