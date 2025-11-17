package com.example.taskmanager.exception;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorApiResponse {

    private final String error;
    private final String message;
    private final String path;
    private final String timestamp;
}