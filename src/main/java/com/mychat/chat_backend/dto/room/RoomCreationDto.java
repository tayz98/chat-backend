package com.mychat.chat_backend.dto.room;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for creating a new Room
 */
public class RoomCreationDto {
    @NotBlank
    private String description;
    @NotBlank
    private Boolean isPrivate;
    @NotBlank
    @Size(min = 5, max = 30)
    private String password;

    public RoomCreationDto() {
    }

    public RoomCreationDto(String description, Boolean isPrivate, String password) {
        this.description = description;
        this.isPrivate = isPrivate;
        this.password = password;
    }

    // Getters and Setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Builder {
        private String description;
        private Boolean isPrivate;
        private String password;

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public RoomCreationDto build() {
            RoomCreationDto dto = new RoomCreationDto();
            dto.setDescription(description);
            dto.setPrivate(isPrivate);
            dto.setPassword(password);
            return dto;
        }
    }
}
