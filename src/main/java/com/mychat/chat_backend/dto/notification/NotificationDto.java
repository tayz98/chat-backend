package com.mychat.chat_backend.dto.notification;

import com.mychat.chat_backend.model.NotificationType;

import java.time.Instant;

public class NotificationDto {
    private Long id;
    private String content;
    private Instant timestamp;
    private Instant editedTimestamp;
    private Boolean isRead;
    private Long recipientId;
    private NotificationType type;


    public NotificationDto() {
    }

    public NotificationDto(long id, String content, Instant timestamp, Boolean isRead, long recipientId, NotificationType type) {
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

    public void setId(long id) {
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

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
