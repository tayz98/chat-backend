package com.mychat.chat_backend.model.enums;

/**
 * Participant role enum
 * Used to define roles of participants in a chat
 */
public enum ParticipantRole {
    OWNER, // typically the creator of the chat, but can be reassigned
    MODERATOR, // has elevated privileges to manage the chat
    MEMBER, // regular participant in the chat
}
