package com.example.petfinder.exceptions;

public class EntityIsAlreadyExists extends RuntimeException {
    private static final String ENTITY_IS_ALREADY_EXISTS = "Entity is already exists";

    public EntityIsAlreadyExists(String message) {
        super(message.isEmpty() ? ENTITY_IS_ALREADY_EXISTS : message);
    }

    public EntityIsAlreadyExists() {
        super(ENTITY_IS_ALREADY_EXISTS);
    }


}
