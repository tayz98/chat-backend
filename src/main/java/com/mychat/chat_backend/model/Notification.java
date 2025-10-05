package com.mychat.chat_backend.model;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * Notification entity
 * A notification is sent to a user when specific events happen
 * The types of events are defined in NotificationType enum
 */
@Entity
@Table(name = "chat_notification")
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_seq_gen", sequenceName = "notification_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "message", nullable = false, length = 255)
    private String content;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private Instant timestamp;

    @Column(name = "read_status")
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User recipient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    protected Notification() {
    }

    public Notification(String content, User recipient, NotificationType type) {
        this.content = content;
        this.recipient = recipient;
        this.type = type;
        this.isRead = false;
        this.timestamp = Instant.now();
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

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
