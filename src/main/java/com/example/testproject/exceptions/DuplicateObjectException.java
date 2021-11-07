package com.example.testproject.exceptions;

public class DuplicateObjectException extends RuntimeException {
    public DuplicateObjectException(String message) {
        super(message);
    }
}
