package com.mychat.chat_backend.dto.user;

import java.time.Instant;
import java.util.List;

public class UserUpdateDto {
    private String email;
    private String password;
    private String avatarUrl;
    private Instant lastLogin;
    private Instant lastLogout;
    private Boolean isOnline;
    private Boolean isAdmin;
    private List<Long> currentRooms;

    public UserUpdateDto() {
    }

    public UserUpdateDto(String email, String password, String avatarUrl, Instant lastLogin, Instant lastLogout, Boolean isOnline, Boolean isAdmin, List<Long> currentRooms) {
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.lastLogin = lastLogin;
        this.lastLogout = lastLogout;
        this.isOnline = isOnline;
        this.isAdmin = isAdmin;
        this.currentRooms = currentRooms;
    }

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

    public List<Long> getCurrentRooms() {
        return currentRooms;
    }

    public void setCurrentRooms(List<Long> currentRooms) {
        this.currentRooms = currentRooms;
    }
}
