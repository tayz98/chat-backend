package com.mychat.chat_backend.dto.message;

import jakarta.validation.constraints.NotBlank;

public class MessageUpdateDto {
    @NotBlank
    private String content;

    public MessageUpdateDto() {
    }

    public MessageUpdateDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
