package com.mychat.chat_backend.dto.user;

import java.time.Instant;
import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for updating user information.
 * UPDATE request payload
 */
public class UserUpdateDto {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 8, max = 100)
    private String password;
    @NotNull
    private String avatarUrl;
    @NotNull
    @PastOrPresent
    private Instant lastLogin;
    @NotNull
    @PastOrPresent
    private Instant lastLogout;
    @NotNull
    private Boolean isOnline;
    @NotNull
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
}
