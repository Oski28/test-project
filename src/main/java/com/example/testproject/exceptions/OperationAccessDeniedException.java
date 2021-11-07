package com.example.testproject.exceptions;

public class OperationAccessDeniedException extends RuntimeException {
    public OperationAccessDeniedException(String message) {
        super(message);
    }
}
