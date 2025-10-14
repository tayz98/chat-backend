package com.mychat.chat_backend.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RoomUpdateDto {
    @NotBlank
    private String description;
    @NotNull
    private Boolean isPrivate;
    @NotNull
    private Long ownerId;
    @NotBlank
    @Size(min = 5, max = 30)
    private String password;
    @NotNull
    @Size(min = 1, max = 20)
    private List<Long> participants;
    @NotNull
    @Size(min = 1, max = 20)
    private List<String> allowedUsernames;

    private RoomUpdateDto() {
    }

    public RoomUpdateDto(String description, Boolean isPrivate, Long ownerId, String password, List<Long> participants, List<String> allowedUsernames) {
        this.description = description;
        this.isPrivate = isPrivate;
        this.ownerId = ownerId;
        this.password = password;
        this.participants = participants;
        this.allowedUsernames = allowedUsernames;
    }

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    public List<String> getAllowedUsernames() {
        return allowedUsernames;
    }

    public void setAllowedUsernames(List<String> allowedUsernames) {
        this.allowedUsernames = allowedUsernames;
    }
}
