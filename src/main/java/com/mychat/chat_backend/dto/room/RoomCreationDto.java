package com.mychat.chat_backend.dto.room;

public class RoomCreationDto {
    private String description;
    private Boolean isPrivate;
    private Long ownerId;
    private String password;


    public RoomCreationDto() {
    }

    public RoomCreationDto(String description, Boolean isPrivate, long ownerId, String password) {
        this.description = description;
        this.isPrivate = isPrivate;
        this.ownerId = ownerId;
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
