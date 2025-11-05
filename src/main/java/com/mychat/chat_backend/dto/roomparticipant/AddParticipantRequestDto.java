package com.mychat.chat_backend.dto.roomparticipant;

import com.mychat.chat_backend.model.enums.ParticipantRole;

import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for adding a participant to a room
 * Update payload
 */
public class AddParticipantRequestDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Role is required")
    private ParticipantRole role;

    public AddParticipantRequestDto() {
    }

    public AddParticipantRequestDto(Long userId, ParticipantRole role) {
        this.userId = userId;
        this.role = role;
    }

    // Getters and Setters

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
}