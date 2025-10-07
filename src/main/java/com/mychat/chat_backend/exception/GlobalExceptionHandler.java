package com.mychat.chat_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {RoomNotFoundException.class, MessageNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorObject> handleNotFoundException(Exception e) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), e.getMessage(), Instant.now());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
}
