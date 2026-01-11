package com.mychat.chat_backend.dto.friendship;

import java.time.Instant;
import java.util.Objects;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for Friendship entity
 * READ response payload
 */
public class FriendshipDto {
    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long friendId;
    @NotBlank
    private String friendName;
    @NotNull
    @PastOrPresent
    private Instant establishedOn;

    public FriendshipDto(Long id, Long userId, Long friendId, String friendName, Instant establishedOn) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.friendName = friendName;
        this.establishedOn = establishedOn;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Instant getEstablishedOn() {
        return establishedOn;
    }

    public void setEstablishedOn(Instant establishedOn) {
        this.establishedOn = establishedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FriendshipDto))
            return false;
        FriendshipDto that = (FriendshipDto) o;
        return id != null && id.equals(that.id) && userId.equals(that.userId)
                && friendId.equals(that.friendId)
                && friendName.equals(that.friendName)
                && establishedOn.equals(that.establishedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, friendId, friendName, establishedOn);
    }

}
