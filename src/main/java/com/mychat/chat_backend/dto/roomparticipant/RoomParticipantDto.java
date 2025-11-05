package com.mychat.chat_backend.dto.roomparticipant;

import java.time.Instant;

import com.mychat.chat_backend.dto.room.RoomSummaryDto;
import com.mychat.chat_backend.dto.user.UserSummaryDto;
import com.mychat.chat_backend.model.enums.ParticipantRole;

/**
 * DTO for transferring room participant information
 */
public class RoomParticipantDto {
    private Long id;
    private UserSummaryDto user;
    private RoomSummaryDto room;
    private ParticipantRole role;
    private Instant createdOn;

    public RoomParticipantDto() {
    }

    public RoomParticipantDto(Long id, RoomSummaryDto room, ParticipantRole role, UserSummaryDto user,
            Instant createdOn) {
        this.id = id;
        this.room = room;
        this.role = role;
        this.user = user;
        this.createdOn = createdOn;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomSummaryDto getRoom() {
        return room;
    }

    public void setRoom(RoomSummaryDto room) {
        this.room = room;
    }

    public UserSummaryDto getUser() {
        return user;
    }

    public void setUser(UserSummaryDto user) {
        this.user = user;
    }

    public ParticipantRole getRole() {
        return role;
    }

    public void setRole(ParticipantRole role) {
        this.role = role;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

}