package com.mychat.chat_backend.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity
 * A user can exchange messages with other users in rooms
 * A user can be in multiple rooms at the same time
 */
@Entity
@Table(name = "chat_user")
public class User {

    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @Column(name = "id")
    private long id;

    @Column(unique = true, name = "user_name", nullable = false, length = 50, updatable = false)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Timestamp
    @Column(name = "last_login")
    private Instant lastLogin;

    @Timestamp
    @Column(name = "last_logout")
    private Instant lastLogout;

    @CreationTimestamp
    private Instant createdOn;

    @CreationTimestamp
    private Instant updatedOn;

    @Column(unique = true, name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "avatarUrl", length = 100)
    private String avatarUrl;

    @Column(name = "online_status", nullable = false, length = 1)
    private Boolean isOnline;

    @Column(name = "admin_status", nullable = false, length = 1)
    private Boolean isAdmin;

    @ManyToMany(mappedBy = "participants")
    List<Room> currentRooms;

    protected User() {
    }

    public User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.isAdmin = builder.isAdmin;
        this.isOnline = false;
        this.avatarUrl = "placeholder";
        this.currentRooms = new ArrayList<>();
        this.createdOn = Instant.now();
    }

    // GETTERS AND SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant created) {
        this.createdOn = created;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn() {
        this.updatedOn = Instant.now();
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


    public List<Room> getCurrentRooms() {
        return currentRooms;
    }

    public void setCurrentRooms(List<Room> currentRooms) {
        this.currentRooms = currentRooms;
    }

    public void addRoom(Room room) {
        currentRooms.add(room);
    }

    public void removeRoom(Room room) {
        currentRooms.remove(room);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public static class Builder {
        private String username;
        private String password;
        private String email;
        private Boolean isAdmin;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder isAdmin(Boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public User build() {
            if (username == null || password == null || email == null) {
                throw new IllegalArgumentException("Username, password and email must not be null");
            }
            // further validation can be added here
            return new User(this);
        }
    }
}