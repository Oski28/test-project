package com.example.testproject.exceptions;

public class CollectionSizeException extends RuntimeException {
    public CollectionSizeException(String message) {
        super(message);
    }
}
