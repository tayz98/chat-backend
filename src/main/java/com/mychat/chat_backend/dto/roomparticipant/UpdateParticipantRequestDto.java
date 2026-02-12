package com.mychat.chat_backend.dto.roomparticipant;

import com.mychat.chat_backend.model.enums.ParticipantRole;

public class UpdateParticipantRequestDto {
    private ParticipantRole role;

    public UpdateParticipantRequestDto() {
    }

    public UpdateParticipantRequestDto(ParticipantRole role) {
        this.role = role;
    }

    // Getters and Setters
    public ParticipantRole getRole() {
        return role;
    }

    public void setRole(ParticipantRole role) {
        this.role = role;
    }

    public static class Builder {
        private ParticipantRole role;

        public Builder role(ParticipantRole role) {
            this.role = role;
            return this;
        }

        public UpdateParticipantRequestDto build() {
            UpdateParticipantRequestDto dto = new UpdateParticipantRequestDto();
            dto.setRole(role);
            return dto;
        }
    }
}
