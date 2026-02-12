package com.mychat.chat_backend.dto.user;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserSummaryDto that = (UserSummaryDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(avatarUrl, that.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, avatarUrl);
    }

    public static class Builder {
        private Long id;
        private String username;
        private String avatarUrl;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public UserSummaryDto build() {
            return new UserSummaryDto(id, username, avatarUrl);
        }
    }
}
