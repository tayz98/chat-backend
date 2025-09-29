package com.mychat.chat_backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "email")
    private List<String> allowedEmails;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

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
        participants.add(owner);
        this.allowedEmails = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return this.owner.getId();
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public String getPassword() {
        return password;
    }

    public List<User> getParticipantIds() {
        return participants;
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

    public void setParticipantIds(List<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(User participant) {
        this.participants.remove(participant);
    }

    public void clearParticipants() {
        this.participants.clear();
    }
}
