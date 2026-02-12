package com.mychat.chat_backend.dto.user;

import java.time.Instant;
import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for user information.
 * READ response payload
 */
public class UserDto {
    @NotNull
    UserSummaryDto summary;
    @Email
    private String email;
    @NotNull
    private Instant created;
    @NotNull
    private Instant lastLogin;
    @NotNull
    private Boolean isOnline;
    @NotNull
    private Boolean isAdmin;

    public UserDto() {
    }

    public UserDto(UserSummaryDto summary, String email, Instant created, Instant lastLogin,
            Boolean isOnline, Boolean isAdmin) {
        this.summary = summary;
        this.email = email;
        this.created = created;
        this.lastLogin = lastLogin;
        this.isOnline = isOnline;
        this.isAdmin = isAdmin;
    }

    // Getters and Setters
    public UserSummaryDto getSummary() {
        return summary;
    }

    public void setSummary(UserSummaryDto summary) {
        this.summary = summary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDto userDto = (UserDto) o;
        return summary.equals(userDto.summary) &&
                email.equals(userDto.email) &&
                created.equals(userDto.created) &&
                lastLogin.equals(userDto.lastLogin) &&
                isOnline.equals(userDto.isOnline) &&
                isAdmin.equals(userDto.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary, email, created, lastLogin, isOnline, isAdmin);
    }

    public static class Builder {
        private Long id;
        private String username;
        private String avatarUrl;
        private String email;
        private Instant created;
        private Instant lastLogin;
        private Boolean isOnline;
        private Boolean isAdmin;

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

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder lastLogin(Instant lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public Builder isOnline(Boolean isOnline) {
            this.isOnline = isOnline;
            return this;
        }

        public Builder isAdmin(Boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public UserDto build() {
            UserDto dto = new UserDto();
            UserSummaryDto summary = new UserSummaryDto(id, username, avatarUrl != null ? avatarUrl : "placeholder");
            dto.setSummary(summary);
            dto.setEmail(email);
            dto.setCreated(created != null ? created : Instant.now());
            dto.setLastLogin(lastLogin != null ? lastLogin : Instant.now());
            dto.setIsOnline(Objects.requireNonNullElse(isOnline, false));
            dto.setIsAdmin(Objects.requireNonNullElse(isAdmin, false));
            return dto;
        }
    }
}
