package com.mychat.chat_backend.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.Instant;

/**
 * Data Transfer Object for Message entity
 */
public class MessageDto {
    @NotNull
    private Long id;

    @NotBlank
    private String content;

    @NotNull
    private Boolean isDeleted;

    @NotNull
    private Long senderId;

    @NotBlank
    private String senderUsername;

    @NotNull
    private Long roomId;

    @NotNull
    @PastOrPresent
    private Instant timestamp;

    @PastOrPresent
    private Instant editedTimestamp;


    private MessageDto() {
    }

    public MessageDto(long id, String content, long senderId, String senderUsername, long roomId, Instant timestamp, Instant editedTimestamp, Boolean isDeleted) {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.roomId = roomId;
        this.timestamp = timestamp;
        this.editedTimestamp = editedTimestamp;
        this.isDeleted = isDeleted;
    }

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

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
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
