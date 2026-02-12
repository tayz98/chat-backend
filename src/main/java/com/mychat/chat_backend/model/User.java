package com.mychat.chat_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mychat.chat_backend.model.enums.ParticipantRole;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User entity
 * A user can exchange messages with other users in rooms
 * A user can be in multiple rooms at the same time
 */
@Entity
@Table(name = "chat_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(unique = true, name = "user_name", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "last_login", nullable = true)
    private Instant lastLogin;

    @Column(name = "last_logout", nullable = true)
    private Instant lastLogout;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant updatedOn;

    @Column(unique = true, name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "avatarUrl", length = 100)
    private String avatarUrl;

    @Column(name = "online_status", nullable = false)
    private Boolean isOnline;

    @Column(name = "admin_status", nullable = false)
    private Boolean isAdmin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomParticipant> roomParticipations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdOn DESC")
    private List<Notification> notifications = new ArrayList<>();

    protected User() {
    }

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.isAdmin = Objects.requireNonNullElse(builder.isAdmin, false);
        this.isOnline = Objects.requireNonNullElse(builder.isOnline, false);
        this.avatarUrl = Objects.requireNonNullElse(builder.avatarUrl, "placeholder");
        this.roomParticipations = new HashSet<>();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn() {
        this.updatedOn = Instant.now();
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

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
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

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<RoomParticipant> getRoomParticipations() {
        return roomParticipations;
    }

    public Set<Room> getCurrentRooms() {
        return roomParticipations.stream()
                .map(RoomParticipant::getRoom)
                .collect(Collectors.toSet());
    }

    public RoomParticipant joinRoom(Room room, ParticipantRole role) {
        RoomParticipant participation = new RoomParticipant(room, this, role);
        roomParticipations.add(participation);
        return participation;
    }

    public void leaveRoom(Room room) {
        roomParticipations.removeIf(p -> p.getRoom().getId().equals(room.getId()));
    }

    public boolean isInRoom(Room room) {
        return roomParticipations.stream()
                .anyMatch(p -> p.getRoom().getId().equals(room.getId()));
    }

    public void setRoomParticipations(Set<RoomParticipant> roomParticipations) {
        this.roomParticipations = roomParticipations;
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

    public static class Builder {
        private String username;
        private String password;
        private String email;
        private Boolean isAdmin;
        private Boolean isOnline;
        private String avatarUrl;

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

        public Builder isOnline(Boolean isOnline) {
            this.isOnline = isOnline;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public User build() {
            if (username == null || password == null || email == null) {
                throw new IllegalArgumentException("Username, password and email must not be null");
            }
            return new User(this);
        }
    }
}