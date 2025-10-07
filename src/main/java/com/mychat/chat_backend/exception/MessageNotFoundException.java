package com.mychat.chat_backend.exception;

import java.io.Serial;

public class MessageNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Message not found";

    public MessageNotFoundException() {
        super(MESSAGE);
    }
}
