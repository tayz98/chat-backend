package com.mychat.chat_backend.dto.friendship;

import java.time.Instant;
import com.mychat.chat_backend.model.enums.FriendshipStatus;
import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for FriendshipRequest entity
 * CREATE/READ/UPDATE response payload
 */
public class FriendshipRequestDto {
    @NotNull
    private Long id;
    @NotNull
    private Long requesterId;
    @NotBlank
    private String requesterName;
    @NotNull
    private Long addresseeId;
    @NotBlank
    private String addresseeName;
    @NotNull
    @PastOrPresent
    private Instant requestedAt;
    private Instant respondedAt;
    @NotBlank
    private FriendshipStatus statusOfRequest;

    public FriendshipRequestDto() {
    }

    @SuppressWarnings("java:S107")
    public FriendshipRequestDto(Long id, Long requesterId, Long addresseeId, String requesterName,
            String addresseeName, Instant requestedAt, Instant respondedAt, FriendshipStatus statusOfRequest) {
        this.id = id;
        this.requesterId = requesterId;
        this.addresseeId = addresseeId;
        this.requesterName = requesterName;
        this.addresseeName = addresseeName;
        this.requestedAt = requestedAt;
        this.respondedAt = respondedAt;
        this.statusOfRequest = statusOfRequest;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public Long getAddresseeId() {
        return addresseeId;
    }

    public void setAddresseeId(Long addresseeId) {
        this.addresseeId = addresseeId;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getAddresseeName() {
        return addresseeName;
    }

    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Instant getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(Instant respondedAt) {
        this.respondedAt = respondedAt;
    }

    public FriendshipStatus getStatusOfRequest() {
        return statusOfRequest;
    }

    public void setStatusOfRequest(FriendshipStatus statusOfRequest) {
        this.statusOfRequest = statusOfRequest;
    }

}
