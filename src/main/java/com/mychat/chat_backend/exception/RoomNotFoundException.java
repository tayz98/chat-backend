package com.mychat.chat_backend.exception;

import java.io.Serial;

public class RoomNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Room not found";

    public RoomNotFoundException() {
        super(MESSAGE);
    }
}
