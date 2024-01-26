package com.example.petfinder.exceptions;

public class InvalidPasswordException extends RuntimeException {
    private static final String INVALID_PASSWORD = "Invalid old password";

    public InvalidPasswordException(String message) {
        super(message.isEmpty() ? INVALID_PASSWORD : message);
    }

    public InvalidPasswordException() {
        super(INVALID_PASSWORD);
    }
}
