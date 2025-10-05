package com.mychat.chat_backend.model;

import jakarta.persistence.*;

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
    @SequenceGenerator(name = "message_seq_gen", sequenceName = "message_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq_gen")
    @Column(name = "id")
    private long id;

    @Column(nullable = false, updatable = true, name = "content")
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, name = "sender")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false, updatable = false, name = "timestamp")
    private Instant timestamp;

    @Column(name = "edited_timestamp", nullable = true, updatable = true)
    private Instant editedTimestamp;

    // TODO: maybe use a isDelete flag to show the message has been deleted in the chat
    // or think of a different way to show deleted messages
    // maybe a message history table inside the room entity
    // or don't delete the message at all, just mark it as deleted

    public Message() {
    }

    public Message(String content, User user, Room room) {
        this.content = content;
        this.user = user;
        this.room = room;
        this.timestamp = Instant.now();
    }

    // GETTERS AND SETTERS

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

    public Instant getEditedTimestamp() {
        return editedTimestamp;
    }

    public void setEditedTimestamp(Instant editedTimestamp) {
        this.editedTimestamp = editedTimestamp;
    }

}