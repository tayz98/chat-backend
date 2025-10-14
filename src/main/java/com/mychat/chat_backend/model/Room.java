package com.mychat.chat_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Room entity
 * A room contains messages exchanged between users
 * For private chats (e.g., between friends) or groups, a room can be set to private
 */
@Entity
@Table(name = "chat_room")
public class Room {

    @Id
    @SequenceGenerator(name = "room_seq_gen", sequenceName = "room_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq_gen")
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
    @Column(nullable = false, name = "password")
    private String password;

    @JoinColumn(name = "owner")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User owner;

    @ManyToMany
    @JoinTable(name = "participants_room", joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "user_id"}))
    private List<User> participants;

    //    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(
//            name = "room_allowed_emails",
//            joinColumns = @JoinColumn(name = "room_id")
//    )
    @Column(name = "allowed_users")
    private List<String> allowedUserNicknames;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    protected Room() {
    }

    Room(
            Builder builder
    ) {
        this.description = builder.description;
        this.isPrivate = builder.isPrivate;
        this.password = builder.password;
        this.owner = builder.owner;
        this.participants = new ArrayList<>();
        createdOn = Instant.now();
        participants.add(owner);
        this.allowedUserNicknames = new ArrayList<>();
        allowedUserNicknames.add(owner.getUsername());
    }

    // GETTERS AND SETTERS

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

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }

    public void clearParticipants() {
        this.participants.clear();
    }

    public void removeParticipant(User participant) {
        this.participants.remove(participant);
    }

    public List<String> getAllowedUserNicknames() {
        return allowedUserNicknames;
    }

    public void setAllowedUserNicknames(List<String> allowedUserNicknames) {
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

    public long getOwnerId() {
        return this.owner.getId();
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public static class Builder {
        private String description;
        private Boolean isPrivate;
        private String password;
        private User owner;

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

        public Builder owner(User owner) {
            this.owner = owner;
            return this;
        }

        public Room build() {
            if (description == null || owner == null) {
                throw new IllegalArgumentException("Description and owner must not be null");
            }
            return new Room(this);
        }
    }
}
