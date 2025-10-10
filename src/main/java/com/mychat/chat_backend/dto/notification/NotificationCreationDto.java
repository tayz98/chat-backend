package com.mychat.chat_backend.dto.notification;

import com.mychat.chat_backend.model.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationCreationDto {
    @NotBlank
    private String content;
    @NotNull
    private NotificationType type;
    @NotNull
    private Long recipientId;

    public NotificationCreationDto() {
    }

    public NotificationCreationDto(String content, NotificationType type, long recipientId) {
        this.content = content;
        this.type = type;
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }
}
