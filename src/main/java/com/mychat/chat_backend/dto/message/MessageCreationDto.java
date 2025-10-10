package com.mychat.chat_backend.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    public MessageCreationDto(long senderId, long roomId, String content) {
        this.senderId = senderId;
        this.roomId = roomId;
        this.content = content;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
