package com.mychat.chat_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Message entity
 * A message is sent by a user to a room
 * and can be seen by all users in the room
 */
@Entity
@Table(name = "chat_message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, updatable = true, name = "content")
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, name = "sender")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant updatedOn;

    @Column(name = "deleted_status", nullable = false)
    private Boolean isDeleted; // to mark a message as deleted in a chat room

    protected Message() {
    }

    Message(Builder builder) {
        this.content = builder.content;
        this.user = builder.user;
        this.room = builder.room;
        this.createdOn = Instant.now();
        this.isDeleted = false;
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getSenderId() {
        return this.user.getId();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        this.isDeleted = deleted;
    }

    public static class Builder {
        private String content;
        private User user;
        private Room room;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder room(Room room) {
            this.room = room;
            return this;
        }

        public Message build() {
            if (content == null || user == null || room == null) {
                throw new IllegalArgumentException("Content, user and room must not be null");
            }
            // further validation can be added here
            return new Message(this);
        }
    }
}