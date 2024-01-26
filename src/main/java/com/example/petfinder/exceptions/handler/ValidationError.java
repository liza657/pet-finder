package com.example.petfinder.exceptions.handler;

public record ValidationError(String objectName,
                              String fieldName,
                              String rejectedValue,
                              String errorMessage) {

}
