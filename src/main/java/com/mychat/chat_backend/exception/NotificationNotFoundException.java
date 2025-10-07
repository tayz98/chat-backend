package com.mychat.chat_backend.exception;


import java.io.Serial;

public class NotificationNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Notification not found";

    public NotificationNotFoundException() {
        super(MESSAGE);
    }
}
