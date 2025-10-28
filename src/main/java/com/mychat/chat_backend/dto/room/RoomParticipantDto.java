package com.mychat.chat_backend.dto.room;

import java.time.Instant;

import com.mychat.chat_backend.model.enums.ParticipantRole;

/**
 * DTO for transferring room participant information
 */
public class RoomParticipantDto {
    private Long id;
    private Long roomId;
    private Long userId;
    private ParticipantRole role;
    private Instant createdOn;
    private Instant joinedAt;

    public RoomParticipantDto() {
    }

    public RoomParticipantDto(Long id, Long roomId, Long userId, ParticipantRole role, Instant createdOn) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.role = role;
        this.createdOn = createdOn;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ParticipantRole getRole() {
        return role;
    }

    public void setRole(ParticipantRole role) {
        this.role = role;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

}