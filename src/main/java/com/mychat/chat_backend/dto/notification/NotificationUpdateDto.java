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

    public static class Builder {
        private Boolean isRead;

        public Builder isRead(Boolean isRead) {
            this.isRead = isRead;
            return this;
        }

        public NotificationUpdateDto build() {
            NotificationUpdateDto dto = new NotificationUpdateDto();
            dto.setRead(isRead);
            return dto;
        }
    }
}
