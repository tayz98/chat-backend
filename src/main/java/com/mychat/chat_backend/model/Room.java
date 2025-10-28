package com.mychat.chat_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Room entity
 * A room contains messages exchanged between users
 * For private chats (e.g., between friends) or groups, a room can be set to
 * private
 */
@Entity
@Table(name = "chat_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "description")
    private String description;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant updatedOn;

    @Column(nullable = false, name = "private_status")
    private Boolean isPrivate;
    @Column(nullable = true, name = "password")
    private String password;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomParticipant> participants;

    @Column(name = "allowed_users")
    private Set<String> allowedUserNicknames;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    protected Room() {
    }

    Room(
            Builder builder) {
        this.description = builder.description;
        this.isPrivate = builder.isPrivate;
        this.password = builder.password;
        this.participants = new HashSet<>();
        createdOn = Instant.now();
        this.allowedUserNicknames = new HashSet<>();
    }

    // Getters and Setters

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn() {
        this.updatedOn = Instant.now();
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public Set<RoomParticipant> getParticipants() {
        return participants;
    }

    public Set<User> getParticipantUsers() {
        return participants.stream()
                .map(RoomParticipant::getUser)
                .collect(Collectors.toSet());
    }

    public void setParticipants(Set<RoomParticipant> participants) {
        this.participants = participants;
    }

    public boolean hasParticipant(User user) {
        return participants.stream()
                .anyMatch(p -> p.getUser().getId().equals(user.getId()));
    }

    public Set<String> getAllowedUserNicknames() {
        return allowedUserNicknames;
    }

    public void setAllowedUserNicknames(Set<String> allowedUserNicknames) {
        this.allowedUserNicknames = allowedUserNicknames;
    }

    public void addAllowedUserNickname(String allowedUserNickname) {
        this.allowedUserNicknames.add(allowedUserNickname);
    }

    public void removeAllowedUserNickname(String allowedUserNickname) {
        this.allowedUserNicknames.remove(allowedUserNickname);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Builder {
        private String description;
        private Boolean isPrivate;
        private String password;

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Room build() {
            if (description == null || isPrivate == null) {
                throw new IllegalArgumentException("Description and private status must not be null");
            }
            return new Room(this);
        }
    }
}
