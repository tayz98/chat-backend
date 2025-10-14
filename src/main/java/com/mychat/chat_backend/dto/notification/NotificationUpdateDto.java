package com.mychat.chat_backend.dto.notification;

import com.mychat.chat_backend.model.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationUpdateDto {
    @NotBlank
    private String content;
    @NotNull
    private Boolean isRead;
    @NotNull
    private NotificationType type;

    private NotificationUpdateDto() {
    }

    public NotificationUpdateDto(String content, NotificationType type, Boolean isRead) {
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

    public Boolean isRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
