package com.mychat.chat_backend.dto.notification;

import com.mychat.chat_backend.model.NotificationType;

import java.time.Instant;

public class NotificationUpdateDto {
    private String content;
    private boolean isRead;
    private NotificationType type;

    public NotificationUpdateDto() {
    }

    public NotificationUpdateDto(String content, NotificationType type, boolean isRead) {
        this.content = content;
        this.type = type;
        this.isRead = isRead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
