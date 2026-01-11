package com.mychat.chat_backend.dto.room;

import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

/**
 * Data Transfer Object for Room entity.
 */
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
    @Size(min = 1, max = 20)
    private Set<Long> participantIds;
    @NotNull
    @Size(min = 1, max = 20)
    private Set<String> participantNames;
    @NotNull
    @Size(min = 1, max = 20)
    private Set<String> allowedUsernames;

    public RoomDto() {
    }

    public RoomDto(Long id, String description, Instant created, Boolean isPrivate,
            Set<Long> participantIds, Set<String> participantNames, Set<String> allowedUsernames) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.isPrivate = isPrivate;
        this.participantIds = participantIds;
        this.participantNames = participantNames;
        this.allowedUsernames = allowedUsernames;
    }

    // Getters and Setters

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

    public Set<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(Set<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public Set<String> getParticipantNames() {
        return participantNames;
    }

    public void setParticipantNames(Set<String> participantNames) {
        this.participantNames = participantNames;
    }

    public Set<String> getAllowedUsernames() {
        return allowedUsernames;
    }

    public void setAllowedUsernames(Set<String> allowedUsernames) {
        this.allowedUsernames = allowedUsernames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RoomDto roomDto = (RoomDto) o;
        return id.equals(roomDto.id) &&
                description.equals(roomDto.description) &&
                created.equals(roomDto.created) &&
                isPrivate.equals(roomDto.isPrivate) &&
                participantIds.equals(roomDto.participantIds) &&
                participantNames.equals(roomDto.participantNames) &&
                allowedUsernames.equals(roomDto.allowedUsernames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, isPrivate,
                participantIds, participantNames, allowedUsernames);
    }
}
