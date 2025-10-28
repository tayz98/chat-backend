package com.mychat.chat_backend.exception;

import java.io.Serial;

/**
 * FriendshipNotFoundException is thrown
 * when a requested friendship is not found.
 */
public class FriendshipNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Friendship not found";

    /**
     * Constructs a new FriendshipNotFoundException with a default message.
     */
    public FriendshipNotFoundException() {
        super(MESSAGE);
    }
}
