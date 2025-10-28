package com.mychat.chat_backend.model.enums;

/**
 * Notification type enum
 * Used to identify the type of notification
 */
public enum NotificationType {
    FRIEND_REQUEST, // When a friend request is sent
    FRIEND_ACCEPTED, // When a friend request is accepted
    FRIEND_REJECTED, // When a friend request is rejected
    FRIEND_REMOVED, // When a friend is removed
    ROOM_CREATED, // When a room is created
    ROOM_DELETED, // When a room is deleted
    ROOM_JOINED, // When a user joins a room
    ROOM_LEFT, // When a user leaves a room
    ROOM_UPDATED, // When a room is updated
    MENTION, // When a user is mentioned in a message
    SYSTEM; // System notifications
}
