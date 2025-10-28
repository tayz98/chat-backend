package com.mychat.chat_backend.exception;

import java.io.Serial;

/**
 * NotificationNotFoundException is thrown
 * when a requested notification is not found.
 */
public class RoomNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Room not found";

    /**
     * Constructs a new RoomNotFoundException with a default message.
     */
    public RoomNotFoundException() {
        super(MESSAGE);
    }
}
