package com.lucasvignolli.usuario.controller;

import com.lucasvignolli.usuario.infrastructure.exceptions.ConflictExceptions;
import com.lucasvignolli.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.lucasvignolli.usuario.infrastructure.exceptions.UnauthorizedExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictExceptions.class)
    public ResponseEntity<String> handleConflictExceptions(ConflictExceptions ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedExceptions.class)
    public ResponseEntity<String> handleUnauthorizedExceptions(UnauthorizedExceptions ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
