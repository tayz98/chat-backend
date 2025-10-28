package com.mychat.chat_backend.exception;

import java.time.Instant;

/**
 * ErrorObject represents the structure of an error response.
 */
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Instant timestamp;

    public ErrorObject(Integer statusCode, String message, Instant timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
