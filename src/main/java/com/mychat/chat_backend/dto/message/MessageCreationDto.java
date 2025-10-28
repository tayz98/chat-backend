package com.mychat.chat_backend.dto.message;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for creating a new Message
 * CREATE request payload
 */
public class MessageCreationDto {

    @NotNull
    private Long senderId;
    @NotNull
    private Long roomId;
    @NotBlank
    @Size(max = 255)
    private String content;

    public MessageCreationDto() {
    }

    public MessageCreationDto(Long senderId, Long roomId, String content) {
        this.senderId = senderId;
        this.roomId = roomId;
        this.content = content;
    }

    // Getters and Setters

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
