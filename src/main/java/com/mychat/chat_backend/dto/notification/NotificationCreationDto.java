package com.mychat.chat_backend.dto.notification;

import com.mychat.chat_backend.model.enums.NotificationType;
import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for creating a new Notification
 * CREATE request payload
 */
public class NotificationCreationDto {
    @NotNull
    private NotificationType type;
    @NotNull
    private Long recipientId;

    public NotificationCreationDto() {
    }

    public NotificationCreationDto(NotificationType type, Long recipientId) {
        this.type = type;
        this.recipientId = recipientId;
    }

    // Getters and Setters

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }
}
