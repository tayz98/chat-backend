package com.mychat.chat_backend.dto.message;

public class MessageUpdateDto {
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
