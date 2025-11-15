package com.example.taskmanager.exception;


public class ExternalCommunicationException extends RuntimeException {
    public ExternalCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalCommunicationException(Throwable cause) {
        super(cause);
    }
}
