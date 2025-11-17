package com.example.taskmanager.exception;

import lombok.Setter;

public class ErrorApiResponse {

    @Setter
    private String error;
    private final String message;
    private final String path;
    private final String timestamp;

    public ErrorApiResponse(String message, String path, String timestamp) {
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }
}