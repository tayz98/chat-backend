package com.mychat.chat_backend.exception;

import java.io.Serial;

/**
 * FriendshipRequestNotFoundException is thrown
 * when a requested friendship request is not found.
 */
public class FriendshipRequestNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Friendship request not found";

    /**
     * Constructs a new FriendshipRequestNotFoundException with a default message.
     */
    public FriendshipRequestNotFoundException() {
        super(MESSAGE);
    }
}
