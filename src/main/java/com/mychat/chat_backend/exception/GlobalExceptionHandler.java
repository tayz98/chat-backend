package com.mychat.chat_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
/**
 * GlobalExceptionHandler handles specific exceptions and returns structured
 * error responses.
 */
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { RoomNotFoundException.class, MessageNotFoundException.class,
            UserNotFoundException.class })
    /**
     * Handles not found exceptions and constructs an appropriate error response.
     * 
     * @param e the exception that was thrown
     * @return a ResponseEntity containing the error details
     */
    public ResponseEntity<ErrorObject> handleNotFoundException(Exception e) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), e.getMessage(), Instant.now());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
}
