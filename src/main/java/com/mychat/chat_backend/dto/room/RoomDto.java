package com.mychat.chat_backend.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;

public class RoomDto {

    @NotNull
    private Long id;
    @NotBlank
    private String description;
    @NotNull
    @PastOrPresent
    private Instant created;
    @NotNull
    private Boolean isPrivate;
    @NotNull
    private Long ownerId;
    @NotBlank
    @Size(max = 30)
    private String ownerName;
    @NotNull
    @Size(min = 1, max = 20)
    private List<Long> participantIds;
    @NotNull
    @Size(min = 1, max = 20)
    private List<String> participantNames;
    @NotNull
    @Size(min = 1, max = 20)
    private List<String> allowedUsernames;

    private RoomDto() {
    }

    public RoomDto(Long id, String description, Instant created, Boolean isPrivate, Long ownerId, String ownerName, List<Long> participantIds, List<String> participantNames, List<String> allowedUsernames) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setOwnerId(Long ownerId) {
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
