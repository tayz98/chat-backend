package com.mychat.chat_backend.dto.notification;

import com.mychat.chat_backend.model.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.Instant;

public class NotificationDto {
    @NotNull
    private Long id;
    @NotBlank
    private String content;
    @NotNull
    @PastOrPresent
    private Instant timestamp;
    @PastOrPresent
    private Instant editedTimestamp;
    @NotNull
    private Boolean isRead;
    @NotNull
    private Long recipientId;
    @NotNull
    private NotificationType type;


    private NotificationDto() {
    }

    public NotificationDto(Long id, String content, Instant timestamp, Boolean isRead, Long recipientId, NotificationType type) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.recipientId = recipientId;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
