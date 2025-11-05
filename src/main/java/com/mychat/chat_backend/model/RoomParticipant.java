package com.mychat.chat_backend.model;

import jakarta.persistence.*;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mychat.chat_backend.model.enums.ParticipantRole;

@Entity
@Table(name = "room_participant")
/**
 * Entity representing a participant in a chat room.
 */
public class RoomParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant updatedOn;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ParticipantRole role;

    // Additional fields if needed

    protected RoomParticipant() {
    }

    public RoomParticipant(Builder builder) {
        this.room = builder.room;
        this.user = builder.user;
        this.role = builder.role;
        this.createdOn = Instant.now();
    }

    public RoomParticipant(Room room, User user, ParticipantRole role) {
        this.room = room;
        this.user = user;
        this.role = role;
        this.createdOn = Instant.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ParticipantRole getRole() {
        return role;
    }

    public void setRole(ParticipantRole role) {
        this.role = role;
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

    public static class Builder {
        private Room room;
        private User user;
        private ParticipantRole role;

        public Builder room(Room room) {
            this.room = room;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder role(ParticipantRole role) {
            this.role = role;
            return this;
        }

        public RoomParticipant build() {
            if (room == null || user == null || role == null) {
                throw new IllegalStateException("Room, User, and Role must be provided");
            }
            return new RoomParticipant(this);
        }
    }

}
