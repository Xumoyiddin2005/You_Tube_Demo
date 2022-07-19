package com.company.controller;


import com.company.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({BadRequestException.class, ItemNotFoundException.class,
             ProfileNotFoundException.class, ItemAlreadyExistsException.class,
            ProfileAlreadyExists.class,MethodNotAllowedException.class
    })
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
