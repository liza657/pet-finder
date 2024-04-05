package com.example.petfinder.exceptions;

public class PasswordMatchingException extends RuntimeException{
    private static final String PASSWORD_NOT_MATCHING = "Unable to find entity.";

    public PasswordMatchingException(String message) {
        super(message.isEmpty() ? PASSWORD_NOT_MATCHING : message);
    }

}
