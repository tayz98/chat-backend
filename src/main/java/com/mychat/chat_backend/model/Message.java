package com.mychat.chat_backend.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "chat_message")
public class Message {

    @Id
    @SequenceGenerator(name = "message_seq_gen", sequenceName = "message_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq_gen")
    @Column(name = "id")
    private long id;

    @Column(nullable = false, updatable = false, name = "content")
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, name = "sender")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false, updatable = false, name = "timestamp")
    private Instant timestamp;

    protected Message() {
    }

    public Message(long id, String content, User user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}