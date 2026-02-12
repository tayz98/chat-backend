package com.mychat.chat_backend.dto.roomparticipant;

import com.mychat.chat_backend.model.enums.ParticipantRole;

public class RoomParticipantCreationDto {
    private Long userId;
    private Long roomId;
    private ParticipantRole role;

    public RoomParticipantCreationDto() {
    }

    public RoomParticipantCreationDto(Long userId, Long roomId, ParticipantRole role) {
        this.userId = userId;
        this.roomId = roomId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public ParticipantRole getRole() {
        return role;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setRole(ParticipantRole role) {
        this.role = role;
    }

    public static class Builder {
        private Long userId;
        private Long roomId;
        private ParticipantRole role;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder role(ParticipantRole role) {
            this.role = role;
            return this;
        }

        public RoomParticipantCreationDto build() {
            RoomParticipantCreationDto dto = new RoomParticipantCreationDto();
            dto.setUserId(userId);
            dto.setRoomId(roomId);
            dto.setRole(role);
            return dto;
        }
    }
}
