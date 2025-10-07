package com.mychat.chat_backend.dto.room;

import java.util.List;

public class RoomUpdateDto {
    private String description;
    private Boolean isPrivate;
    private Long ownerId;
    private String password;
    private List<Long> participants;
    private List<String> allowedUsernames;

    public RoomUpdateDto() {
    }

    public RoomUpdateDto(String description, Boolean isPrivate, long ownerId, String password, List<Long> participants, List<String> allowedUsernames) {
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

    public void setOwnerId(long ownerId) {
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
