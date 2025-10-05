package com.mychat.chat_backend.dto.room;

import java.time.Instant;
import java.util.List;

public class RoomDto {
    private long id;
    private String description;
    private Instant created;
    private Boolean isPrivate;
    private Long ownerId;
    private String ownerName;
    private List<Long> participantIds;
    private List<String> participantNames;
    private List<String> allowedUsernames;

    public RoomDto() {
    }

    public RoomDto(long id, String description, Instant created, Boolean isPrivate, long ownerId, String ownerName, List<Long> participantIds, List<String> participantNames, List<String> allowedUsernames) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.isPrivate = isPrivate;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.participantIds = participantIds;
        this.participantNames = participantNames;
        this.allowedUsernames = allowedUsernames;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public List<String> getParticipantNames() {
        return participantNames;
    }

    public void setParticipantNames(List<String> participantNames) {
        this.participantNames = participantNames;
    }

    public List<String> getAllowedUsernames() {
        return allowedUsernames;
    }

    public void setAllowedUsernames(List<String> allowedUsernames) {
        this.allowedUsernames = allowedUsernames;
    }
}
