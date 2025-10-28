package com.mychat.chat_backend.exception;

import java.io.Serial;

/**
 * NotificationNotFoundException is thrown
 * when a requested notification is not found.
 */
public class NotificationNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Notification not found";

    /**
     * Constructs a new NotificationNotFoundException with a default message.
     */
    public NotificationNotFoundException() {
        super(MESSAGE);
    }
}
