package com.mychat.chat_backend.dto.room;

import jakarta.validation.constraints.*;

import java.util.Set;

/**
 * Data Transfer Object for updating a chat room.
 */
public class RoomUpdateDto {
    private String description;
    private Boolean isPrivate;
    @Size(min = 5, max = 30)
    private String newPassword;
    @Size(min = 1, max = 20)
    private Set<Long> participants;
    @Size(min = 1, max = 20)
    private Set<String> allowedUsernames;

    public RoomUpdateDto() {
    }

    public RoomUpdateDto(String description, Boolean isPrivate, String password, Set<Long> participants,
            Set<String> allowedUsernames) {
        this.description = description;
        this.isPrivate = isPrivate;
        this.newPassword = password;
        this.participants = participants;
        this.allowedUsernames = allowedUsernames;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String password) {
        this.newPassword = password;
    }

    public Set<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Long> participants) {
        this.participants = participants;
    }

    public Set<String> getAllowedUsernames() {
        return allowedUsernames;
    }

    public void setAllowedUsernames(Set<String> allowedUsernames) {
        this.allowedUsernames = allowedUsernames;
    }

    public static class Builder {
        private String description;
        private Boolean isPrivate;
        private String newPassword;
        private Set<Long> participants;
        private Set<String> allowedUsernames;

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public Builder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public Builder participants(Set<Long> participants) {
            this.participants = participants;
            return this;
        }

        public Builder allowedUsernames(Set<String> allowedUsernames) {
            this.allowedUsernames = allowedUsernames;
            return this;
        }

        public RoomUpdateDto build() {
            RoomUpdateDto dto = new RoomUpdateDto();
            dto.setDescription(description);
            dto.setPrivate(isPrivate);
            dto.setNewPassword(newPassword);
            dto.setParticipants(participants);
            dto.setAllowedUsernames(allowedUsernames);
            return dto;
        }
    }
}
