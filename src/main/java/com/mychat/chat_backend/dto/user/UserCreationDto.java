package com.mychat.chat_backend.dto.user;

import java.time.Instant;

public class UserCreationDto {
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;

    private Instant created;

    public UserCreationDto() {
        created = Instant.now();
    }

    public UserCreationDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        created = Instant.now();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
