package com.mychat.chat_backend.dto.user;

import jakarta.validation.constraints.*;

import java.time.Instant;

public class UserCreationDto {
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    @Size(min = 5, max = 30)
    private String password;
    @NotNull
    private Boolean isAdmin;
    @NotNull
    @PastOrPresent
    private Instant created;

    private UserCreationDto() {
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

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
