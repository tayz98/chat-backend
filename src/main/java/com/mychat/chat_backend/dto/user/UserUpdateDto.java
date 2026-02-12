package com.mychat.chat_backend.dto.user;

import java.time.Instant;
import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for updating user information.
 * UPDATE request payload
 */
public class UserUpdateDto {
    @Email
    private String email;
    @Size(min = 8, max = 100)
    private String password;
    private String avatarUrl;
    @PastOrPresent
    private Instant lastLogin;
    @PastOrPresent
    private Instant lastLogout;
    private Boolean isOnline;
    private Boolean isAdmin;

    public UserUpdateDto() {
    }

    public UserUpdateDto(String email, String password, String avatarUrl, Instant lastLogin, Boolean isOnline,
            Boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.lastLogin = lastLogin;
        this.isOnline = isOnline;
        this.isAdmin = isAdmin;
    }

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Instant getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(Instant lastLogout) {
        this.lastLogout = lastLogout;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public static class Builder {
        private String email;
        private String password;
        private String avatarUrl;
        private Instant lastLogin;
        private Instant lastLogout;
        private Boolean isOnline;
        private Boolean isAdmin;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public Builder lastLogin(Instant lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public Builder lastLogout(Instant lastLogout) {
            this.lastLogout = lastLogout;
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

        public UserUpdateDto build() {
            UserUpdateDto dto = new UserUpdateDto();
            dto.setEmail(email);
            dto.setPassword(password);
            dto.setAvatarUrl(avatarUrl);
            dto.setLastLogin(lastLogin);
            dto.setLastLogout(lastLogout);
            dto.setOnline(isOnline);
            dto.setAdmin(isAdmin);
            return dto;
        }
    }
}
