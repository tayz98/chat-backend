package com.mychat.chat_backend.model.enums;

/**
 * Enum representing the status of a friendship request
 */
public enum FriendshipStatus {
    PENDING, // Initial state when a request is sent
    ACCEPTED, // When the request is accepted
    DECLINED, // When the request is declined
    BLOCKED // When the user is blocked
}
