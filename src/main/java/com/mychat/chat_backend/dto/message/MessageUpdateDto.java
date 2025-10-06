package com.mychat.chat_backend.dto.message;

public class MessageUpdateDto {
    private String content;
    private Long messageId;

    public MessageUpdateDto() {
    }

    public MessageUpdateDto(long messageId, String content) {
        this.content = content;
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
