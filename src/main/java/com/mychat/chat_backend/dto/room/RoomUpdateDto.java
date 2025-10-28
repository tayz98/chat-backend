package com.mychat.chat_backend.dto.room;

import jakarta.validation.constraints.*;

import java.util.Set;

/**
 * Data Transfer Object for updating a chat room.
 */
public class RoomUpdateDto {
    @NotBlank
    private String description;
    @NotNull
    private Boolean isPrivate;
    @NotBlank
    @Size(min = 5, max = 30)
    private String newPassword;
    @NotNull
    @Size(min = 1, max = 20)
    private Set<Long> participants;
    @NotNull
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
}
