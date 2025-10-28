package com.mychat.chat_backend.model.friendship;

import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;

import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.FriendshipStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "friendship_requests", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "requester_id", "addressee_id" })
}, indexes = {
        @Index(name = "idx_requester_addressee", columnList = "requester_id, addressee_id"),
})
/**
 * Entity representing a friendship request
 * between a requester and an addressee.
 */
public class FriendshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false, updatable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressee_id", nullable = false, updatable = false)
    private User addressee;

    @CreationTimestamp
    @Column(name = "requested_at", updatable = false)
    private Instant requestedAt;

    @Column(name = "responded_at", nullable = true, updatable = true)
    private Instant respondedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_of_request", nullable = false, length = 20, updatable = true)
    private FriendshipStatus statusOfRequest;

    private FriendshipRequest(User requester, User addressee) {
        this.requester = requester;
        this.addressee = addressee;
        this.requestedAt = Instant.now();
        this.statusOfRequest = FriendshipStatus.PENDING;
    }

    // Constructor for mapping a DTO to Entity
    public FriendshipRequest(Long id, User requester, User addressee, Instant requestedAt,
            Instant respondedAt, FriendshipStatus statusOfRequest) {
        this.id = id;
        this.requester = requester;
        this.addressee = addressee;
        this.requestedAt = requestedAt;
        this.respondedAt = respondedAt;
        this.statusOfRequest = statusOfRequest;
    }

    /**
     * Factory method to create a new FriendshipRequest
     * 
     * @param requester The user sending the friendship request
     * @param addressee The user receiving the friendship request
     * @return A new FriendshipRequest instance
     * @throws IllegalStateException    if either user is not persisted
     * @throws IllegalArgumentException if requester and addressee are the same user
     */
    public static FriendshipRequest createFriendshipRequest(User requester, User addressee) {
        if (requester == null || addressee == null) {
            throw new IllegalStateException("Users must be persisted before creating friendship request");
        }
        if (requester.getId() == null || addressee.getId() == null) {
            throw new IllegalStateException("Users must be persisted before creating friendship request");
        }
        if (requester.getId().equals(addressee.getId())) {
            throw new IllegalArgumentException("Requester and addressee cannot be the same user");
        }
        return new FriendshipRequest(requester, addressee);
    }

    /**
     * Accepts the friendship request
     * 
     * @return A new Friendship instance representing the accepted friendship
     * @throws IllegalStateException if the request is not pending
     */
    public Friendship acceptRequest() {
        if (this.statusOfRequest != FriendshipStatus.PENDING) {
            throw new IllegalStateException("Can only accept pending requests");
        }
        this.statusOfRequest = FriendshipStatus.ACCEPTED;
        this.respondedAt = Instant.now();
        return Friendship.createFriendship(requester, addressee);
    }

    /**
     * Declines the friendship request
     */
    public void declineRequest() {
        if (this.statusOfRequest != FriendshipStatus.PENDING) {
            throw new IllegalStateException("Can only decline pending requests");
        }
        this.statusOfRequest = FriendshipStatus.DECLINED;
        this.respondedAt = Instant.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public User getRequester() {
        return requester;
    }

    public User getAddressee() {
        return addressee;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public Instant getRespondedAt() {
        return respondedAt;
    }

    public FriendshipStatus getStatusOfRequest() {
        return statusOfRequest;
    }

    public void setStatusOfRequest(FriendshipStatus statusOfRequest) {
        this.statusOfRequest = statusOfRequest;
    }

}
