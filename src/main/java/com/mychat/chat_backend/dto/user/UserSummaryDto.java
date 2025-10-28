package com.mychat.chat_backend.dto.user;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for user summary information.
 * READ response payload, can be used in lists
 */
public class UserSummaryDto {
    @NotNull
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String avatarUrl;

    public UserSummaryDto() {
    }

    public UserSummaryDto(Long id, String username, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}
