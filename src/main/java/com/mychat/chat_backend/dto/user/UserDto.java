package com.mychat.chat_backend.dto.user;

import java.time.Instant;
import java.util.Objects;

import jakarta.validation.constraints.*;

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
}
