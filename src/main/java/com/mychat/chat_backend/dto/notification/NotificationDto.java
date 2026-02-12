package com.mychat.chat_backend.dto.notification;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((editedTimestamp == null) ? 0 : editedTimestamp.hashCode());
        result = prime * result + ((isRead == null) ? 0 : isRead.hashCode());
        result = prime * result + ((recipientId == null) ? 0 : recipientId.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        NotificationDto other = (NotificationDto) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(timestamp, other.timestamp) &&
                Objects.equals(editedTimestamp, other.editedTimestamp) &&
                Objects.equals(isRead, other.isRead) &&
                Objects.equals(recipientId, other.recipientId) &&
                type == other.type;
    }

    public static class Builder {
        private Long id;
        private Instant timestamp;
        private Instant editedTimestamp;
        private Boolean isRead;
        private Long recipientId;
        private NotificationType type;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder editedTimestamp(Instant editedTimestamp) {
            this.editedTimestamp = editedTimestamp;
            return this;
        }

        public Builder isRead(Boolean isRead) {
            this.isRead = isRead;
            return this;
        }

        public Builder recipientId(Long recipientId) {
            this.recipientId = recipientId;
            return this;
        }

        public Builder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public NotificationDto build() {
            NotificationDto dto = new NotificationDto();
            dto.setId(id);
            dto.setTimestamp(timestamp != null ? timestamp : Instant.now());
            dto.setEditedTimestamp(editedTimestamp);
            dto.setRead(isRead != null ? isRead : false);
            dto.setRecipientId(recipientId);
            dto.setType(type);
            return dto;
        }
    }
}
