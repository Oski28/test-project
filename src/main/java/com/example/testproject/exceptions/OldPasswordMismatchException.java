package com.example.testproject.exceptions;

public class OldPasswordMismatchException extends RuntimeException {
    public OldPasswordMismatchException(String message) {
        super(message);
    }
}
