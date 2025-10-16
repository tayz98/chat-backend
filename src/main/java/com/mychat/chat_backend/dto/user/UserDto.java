package com.mychat.chat_backend.dto.user;

import java.time.Instant;
import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Instant created;
    private Instant lastLogin;
    private Instant lastLogout;
    private String avatarUrl;
    private Boolean isOnline;
    private Boolean isAdmin;

    private Set<Long> currentRooms;

    private UserDto() {
    }

    public UserDto(Long id, String username, String email, Instant created, Instant lastLogin, Instant lastLogout, String avatarUrl, Boolean isOnline, Boolean isAdmin, Set<Long> currentRooms) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.created = created;
        this.lastLogin = lastLogin;
        this.lastLogout = lastLogout;
        this.avatarUrl = avatarUrl;
        this.isOnline = isOnline;
        this.isAdmin = isAdmin;
        this.currentRooms = currentRooms;
    }

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

    public Instant getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(Instant lastLogout) {
        this.lastLogout = lastLogout;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public Set<Long> getCurrentRooms() {
        return currentRooms;
    }

    public void setCurrentRooms(Set<Long> currentRooms) {
        this.currentRooms = currentRooms;
    }
}
