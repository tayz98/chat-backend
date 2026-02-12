package com.mychat.chat_backend.dto.message;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.Objects;

import com.mychat.chat_backend.dto.user.UserSummaryDto;

/**
 * Data Transfer Object for Message entity
 * READ response payload
 */
public class MessageDto {
    @NotNull
    private Long id;
    @NotBlank
    private String content;
    @NotNull
    private Boolean isDeleted;
    @NotNull
    private UserSummaryDto sender;
    @NotNull
    private Long roomId;
    @NotNull
    @PastOrPresent
    private Instant timestamp;
    @PastOrPresent
    private Instant editedTimestamp;

    public MessageDto() {
    }

    public MessageDto(Long id, String content, UserSummaryDto sender, Long roomId, Instant timestamp,
            Instant editedTimestamp, Boolean isDeleted) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.roomId = roomId;
        this.timestamp = timestamp;
        this.editedTimestamp = editedTimestamp;
        this.isDeleted = isDeleted;
    }

    // Getters and Setters

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

    public UserSummaryDto getSender() {
        return sender;
    }

    public void setSender(UserSummaryDto sender) {
        this.sender = sender;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((editedTimestamp == null) ? 0 : editedTimestamp.hashCode());
        result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
        result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
        result = prime * result + ((sender == null) ? 0 : sender.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        MessageDto other = (MessageDto) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(content, other.content) &&
                Objects.equals(editedTimestamp, other.editedTimestamp) &&
                Objects.equals(isDeleted, other.isDeleted) &&
                Objects.equals(roomId, other.roomId) &&
                Objects.equals(sender, other.sender) &&
                Objects.equals(timestamp, other.timestamp);
    }

    public static class Builder {
        private Long id;
        private String content;
        private UserSummaryDto sender;
        private Long roomId;
        private Instant timestamp;
        private Instant editedTimestamp;
        private Boolean isDeleted;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder sender(UserSummaryDto sender) {
            this.sender = sender;
            return this;
        }

        public Builder roomId(Long roomId) {
            this.roomId = roomId;
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

        public Builder isDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public MessageDto build() {
            MessageDto dto = new MessageDto();
            dto.setId(id);
            dto.setContent(content);
            dto.setSender(sender);
            dto.setRoomId(roomId);
            dto.setTimestamp(timestamp != null ? timestamp : Instant.now());
            dto.setEditedTimestamp(editedTimestamp);
            dto.setIsDeleted(Objects.requireNonNullElse(isDeleted, false));
            return dto;
        }
    }
}
