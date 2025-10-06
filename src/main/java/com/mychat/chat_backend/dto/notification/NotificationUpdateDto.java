package com.mychat.chat_backend.dto.notification;

import com.mychat.chat_backend.model.NotificationType;

public class NotificationUpdateDto {
    private String content;
    private Boolean isRead;
    private NotificationType type;
    private Long notificationId;

    public NotificationUpdateDto() {
    }

    public NotificationUpdateDto(long notificationId, String content, NotificationType type, boolean isRead) {
        this.content = content;
        this.type = type;
        this.isRead = isRead;
        this.notificationId = notificationId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isRead() {
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
