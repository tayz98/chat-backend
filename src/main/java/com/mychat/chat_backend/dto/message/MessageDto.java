package com.mychat.chat_backend.dto.message;

import jakarta.validation.constraints.*;
import java.time.Instant;

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

    public MessageDto(long id, String content, UserSummaryDto sender, long roomId, Instant timestamp,
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
}
