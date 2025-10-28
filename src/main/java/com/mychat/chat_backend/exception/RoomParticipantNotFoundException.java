package com.mychat.chat_backend.exception;

import java.io.Serial;

/**
 * NotificationNotFoundException is thrown
 * when a requested notification is not found.
 */
public class RoomParticipantNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Room participant not found";

    /**
     * Constructs a new RoomParticipantNotFoundException with a default message.
     */
    public RoomParticipantNotFoundException() {
        super(MESSAGE);
    }
}
