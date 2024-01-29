package com.example.petfinder.exceptions.handler;

import com.example.petfinder.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ExHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(e -> errorMap.put(e.getField(), e.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler(PasswordMatchingException.class)
    public ResponseEntity<Map<String, String>> handlePasswordMatchingException(PasswordMatchingException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("passwordMatching", ex.getMessage());

        return ResponseEntity.badRequest().body(errorMap);
    }

 @ExceptionHandler(EntityNotExistsException.class)
    protected ResponseEntity<Object> handleEntityNotExists(
            EntityNotExistsException exception) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(EntityIsAlreadyExists.class)
    protected ResponseEntity<Object> handleEntityIsAlreadyExists(
            EntityIsAlreadyExists exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(PermissionException.class)
    protected ResponseEntity<Object> handlePermissionException(
            PermissionException exception) {
        return buildResponseEntity(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<Object> handleInvalidTokenException(
            InvalidTokenException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(InvalidPasswordException.class)
    protected ResponseEntity<Object> handleInvalidPasswordException(
            InvalidPasswordException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String exception) {
        ErrorMessage apiError = new ErrorMessage(status);
        apiError.setMessage(exception);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
