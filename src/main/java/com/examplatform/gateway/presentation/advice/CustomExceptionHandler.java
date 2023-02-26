package com.examplatform.gateway.presentation.advice;

import com.examplatform.gateway.presentation.advice.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleInvalid(UserAlreadyExistsException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(ErrorResponse.builder()
                        .message(List.of(e.getMessage()))
                        .status(CONFLICT.value())
                        .build());
    }
}