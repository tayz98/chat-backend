package com.mychat.chat_backend.exception;

import java.io.Serial;

/**
 * MessageNotFoundException is thrown when a requested message is not found.
 */
public class MessageNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Message not found";

    /**
     * Constructs a new MessageNotFoundException with a default message.
     */
    public MessageNotFoundException() {
        super(MESSAGE);
    }
}
