package com.mychat.chat_backend.dto.room;

import jakarta.validation.constraints.*;

public class RoomSummaryDto {
    @NotNull
    private Long id;
    @NotBlank
    private String description;
    @NotNull
    private Boolean isPrivate;

    public RoomSummaryDto() {
    }

    public RoomSummaryDto(Long id, String description, Boolean isPrivate) {
        this.id = id;
        this.description = description;
        this.isPrivate = isPrivate;
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

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    // Getters and Setters

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RoomSummaryDto that = (RoomSummaryDto) o;
        return java.util.Objects.equals(id, that.id) &&
                java.util.Objects.equals(description, that.description) &&
                java.util.Objects.equals(isPrivate, that.isPrivate);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, description, isPrivate);
    }

    public static class Builder {
        private Long id;
        private String description;
        private Boolean isPrivate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public RoomSummaryDto build() {
            return new RoomSummaryDto(id, description, isPrivate);
        }
    }
}
