package com.mychat.chat_backend.dto.friendship;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for the response after creating a friendship request
 * CREATE response payload
 */
public class FriendshipCreationResponseDto {
    @NotNull
    private Long requestId;
    @NotNull
    private Boolean isAccepted;

    public FriendshipCreationResponseDto() {
    }

    public FriendshipCreationResponseDto(Long requestId, Boolean isAccepted) {
        this.requestId = requestId;
        this.isAccepted = isAccepted;
    }

    public Long getRequestId() {
        return requestId;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public void setIsAccepted(Boolean status) {
        this.isAccepted = status;
    }
}
