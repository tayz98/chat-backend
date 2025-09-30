package com.mychat.chat_backend.model;

/**
 * Notification type enum
 * Used to identify the type of notification
 * Types are self-explanatory
 */
public enum NotificationType {
    MESSAGE,
    FRIEND_REQUEST,
    FRIEND_ACCEPTED,
    FRIEND_REJECTED,
    ROOM_CREATED,
    ROOM_DELETED,
    ROOM_JOINED,
    ROOM_LEFT,
    ROOM_UPDATED,
    MENTION,
    SYSTEM;
}
