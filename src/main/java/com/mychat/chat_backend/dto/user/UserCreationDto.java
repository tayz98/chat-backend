package com.mychat.chat_backend.dto.user;

import jakarta.validation.constraints.*;
import java.time.Instant;

/**
 * Data Transfer Object for creating a new User
 * CREATE request payload
 */
public class UserCreationDto {
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    @Size(min = 5, max = 30)
    private String password;
    private Boolean isAdmin;
    @PastOrPresent
    private Instant created;

    public UserCreationDto() {
    }

    public UserCreationDto(String username, String email, String password, Boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        created = Instant.now();
    }

    // Getters and Setters

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

    public static class Builder {
        private String username;
        private String email;
        private String password;
        private Boolean isAdmin;
        private Instant created;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder isAdmin(Boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public UserCreationDto build() {
            UserCreationDto dto = new UserCreationDto();
            dto.setUsername(username);
            dto.setEmail(email);
            dto.setPassword(password);
            dto.setAdmin(isAdmin);
            if (created != null) {
                dto.setCreated(created);
            }
            return dto;
        }
    }
}
