package com.mychat.chat_backend.dto.message;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for updating a Message
 * UPDATE request payload
 */
public class MessageUpdateDto {
    @NotBlank
    private String content;
    @NotNull
    private Boolean isDeleted;

    public MessageUpdateDto() {
    }

    public MessageUpdateDto(String content) {
        this.content = content;
    }

    public MessageUpdateDto(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // Getters and Setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
