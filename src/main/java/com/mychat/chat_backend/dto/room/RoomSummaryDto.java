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

}
