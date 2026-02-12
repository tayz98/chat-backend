package com.mychat.chat_backend.dto.message;

/**
 * Data Transfer Object for updating a Message
 * UPDATE request payload
 */
public class MessageUpdateDto {
    private String content;
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

    public static class Builder {
        private String content;
        private Boolean isDeleted;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder isDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public MessageUpdateDto build() {
            MessageUpdateDto dto = new MessageUpdateDto();
            dto.setContent(content);
            dto.setIsDeleted(isDeleted);
            return dto;
        }
    }
}
