package com.mychat.chat_backend.model;

import jakarta.persistence.*;

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
    private long id;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "created")
    private Instant created;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "room_allowed_emails",
            joinColumns = @JoinColumn(name = "room_id")
    )
    @Column(name = "user_nicknames")
    private List<String> allowedUserNicknames;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    protected Room() {
    }

    public Room(
            long id,
            String description,
            Boolean isPrivate,
            String password,
            User owner
    ) {
        this.id = id;
        this.description = description;
        this.isPrivate = isPrivate;
        this.password = password;
        this.owner = owner;
        this.participants = new ArrayList<>();
        this.created = Instant.now();
        participants.add(owner);
        this.allowedUserNicknames = new ArrayList<>();
    }

    // GETTERS AND SETTERS

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
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

    public void setAllowedUserNicknames(List<String> allowedEmails) {
        this.allowedUserNicknames = allowedEmails;
    }

    public void addAllowedEmail(String email) {
        this.allowedUserNicknames.add(email);
    }

    public void removeAllowedEmail(String email) {
        this.allowedUserNicknames.remove(email);
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
}
