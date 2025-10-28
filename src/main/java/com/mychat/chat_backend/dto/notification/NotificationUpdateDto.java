package com.mychat.chat_backend.dto.notification;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for updating a Notification
 * UPDATE request payload
 */
public class NotificationUpdateDto {

    @NotNull
    private Boolean isRead;

    public NotificationUpdateDto() {
    }

    public NotificationUpdateDto(Boolean isRead) {
        this.isRead = isRead;
    }

    // Getters and Setters

    public Boolean isRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
