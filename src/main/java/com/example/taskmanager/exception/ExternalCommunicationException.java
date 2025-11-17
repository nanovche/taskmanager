package com.example.taskmanager.exception;


public class ExternalCommunicationException extends BaseAppException {
    public ExternalCommunicationException(Throwable cause) {
        super(cause);
    }
    public ExternalCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
