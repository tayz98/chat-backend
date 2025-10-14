package com.mychat.chat_backend.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MessageUpdateDto {
    @NotBlank
    private String content;
    @NotNull
    private Boolean isDeleted;

    private MessageUpdateDto() {
    }

    public MessageUpdateDto(String content) {
        this.content = content;
    }

    public MessageUpdateDto(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

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
