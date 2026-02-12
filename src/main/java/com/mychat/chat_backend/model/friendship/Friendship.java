package com.mychat.chat_backend.model.friendship;

import java.time.Instant;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;

import com.mychat.chat_backend.model.User;

import jakarta.persistence.*;

/**
 * Friendship entity Represents a friendship between two users
 * Uses canonical ordering to prevent duplicate friendships
 */
@Entity
@Table(name = "user_friends", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "other_user_id" })
}, indexes = {
        @Index(name = "idx_user_friend", columnList = "user_id, other_user_id")
})
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "other_user_id", nullable = false, updatable = false)
    private User otherUser;

    @CreationTimestamp
    @Column(name = "established_on", updatable = false)
    private Instant establishedOn;

    protected Friendship() {
    }

    private Friendship(User user, User otherUser) {
        this.user = user;
        this.otherUser = otherUser;
        establishedOn = Instant.now();
        validateCanonicalOrder();
    }

    /**
     * Factory method to create a Friendship with canonical ordering
     *
     * @param user1 First user
     * @param user2 Second user
     * @return Friendship instance with user IDs in canonical order
     * @throws IllegalArgumentException if users are null or the same
     * @throws IllegalStateException    if users are not persisted
     */
    public static Friendship createFriendship(User user1, User user2) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users must not be null");
        }
        if (user1.getId() == null || user2.getId() == null) {
            throw new IllegalStateException("Users must be persisted before creating friendship");
        }
        if (user1.getId().equals(user2.getId())) {
            throw new IllegalArgumentException("A user cannot be friends with themselves");
        }

        // Enforce canonical ordering: lower ID first
        if (user1.getId() < user2.getId()) {
            return new Friendship(user1, user2);
        } else {
            return new Friendship(user2, user1);
        }
    }

    /**
     * Safety check - ensures canonical ordering is maintained
     */
    @PrePersist
    private void validateCanonicalOrder() {
        if (user.getId() != null && otherUser.getId() != null && user.getId() > otherUser.getId()) {
            throw new IllegalStateException(
                    "Canonical ordering violated: userId must be <= otherUserId");
        }
    }

    /**
     * Gets the other user in this friendship
     * 
     * @param currentUser One user in the friendship
     * @return The other user
     * @throws IllegalArgumentException if user is not part of this friendship
     */
    public User getFriendOf(User currentUser) {
        if (this.user.getId().equals(currentUser.getId())) {
            return this.otherUser;
        } else if (this.otherUser.getId().equals(currentUser.getId())) {
            return this.user;
        }
        throw new IllegalArgumentException("User is not part of this friendship");
    }

    /**
     * Checks if a given user is part of this friendship
     */
    public boolean involves(User user) {
        return this.user.getId().equals(user.getId()) ||
                this.otherUser.getId().equals(user.getId());
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public User getOtherUser() {
        return otherUser;
    }

    public Instant getEstablishedOn() {
        return establishedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Friendship))
            return false;
        Friendship that = (Friendship) o;
        return user.getId() != null && otherUser.getId() != null &&
                user.getId().equals(that.user.getId()) &&
                otherUser.getId().equals(that.otherUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId(), otherUser.getId());
    }
}
