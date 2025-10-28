package com.mychat.chat_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mychat.chat_backend.model.enums.NotificationType;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant updatedOn;

    @Column(name = "read_status", nullable = false)
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User recipient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private NotificationType type;

    protected Notification() {
    }

    Notification(Builder builder) {
        this.recipient = builder.recipient;
        this.type = builder.type;
        this.isRead = false;
        this.createdOn = Instant.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
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

    public NotificationType getType() {
        return type;
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

    public static class Builder {
        private User recipient;
        private NotificationType type;

        public Builder recipient(User recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public Notification build() {
            if (recipient == null || type == null) {
                throw new IllegalArgumentException("Recipient and type must not be null");
            }
            return new Notification(this);
        }
    }
}
