package com.mychat.chat_backend.exception;

import java.io.Serial;

/**
 * NotificationNotFoundException is thrown
 * when a requested notification is not found.
 */
public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "User not found";

    /**
     * Constructs a new UserNotFoundException with a default message.
     */
    public UserNotFoundException() {
        super(MESSAGE);
    }
}
