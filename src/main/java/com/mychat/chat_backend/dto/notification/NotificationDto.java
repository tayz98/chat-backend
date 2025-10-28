package com.mychat.chat_backend.dto.notification;

import jakarta.validation.constraints.*;
import java.time.Instant;
import com.mychat.chat_backend.model.enums.NotificationType;

/**
 * Data Transfer Object for Notification entity
 * * READ response payload
 */
public class NotificationDto {
    @NotNull
    private Long id;
    @NotNull
    @PastOrPresent
    private Instant timestamp;
    @NotNull
    @PastOrPresent
    private Instant editedTimestamp;
    @NotNull
    private Boolean isRead;
    @NotNull
    private Long recipientId;
    @NotNull
    private NotificationType type;

    public NotificationDto() {
    }

    public NotificationDto(Long id, Instant timestamp, Boolean isRead, Long recipientId,
            NotificationType type) {
        this.id = id;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.recipientId = recipientId;
        this.type = type;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getEditedTimestamp() {
        return editedTimestamp;
    }

    public void setEditedTimestamp(Instant editedTimestamp) {
        this.editedTimestamp = editedTimestamp;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
