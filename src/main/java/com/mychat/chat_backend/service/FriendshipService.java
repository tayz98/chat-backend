package com.mychat.chat_backend.service;

import java.util.List;
import com.mychat.chat_backend.dto.friendship.*;
import com.mychat.chat_backend.dto.user.UserDto;
import com.mychat.chat_backend.exception.FriendshipNotFoundException;

/**
 * Service interface for managing friendships between users.
 */
public interface FriendshipService {

    /**
     * Get a friendship by its id
     *
     * @param friendshipId Friendship id
     * @return Friendship DTO
     * @throws FriendshipNotFoundException if friendship not found
     */
    FriendshipDto getFriendshipById(Long friendshipId);

    /**
     * Get all friends of a user
     *
     * @param userId User id
     * @return List of friends
     * @throws UserNotFoundException if user not found
     */
    List<UserDto> getFriends(Long userId);

    /**
     * Get all friendships
     *
     * @return List of Friendship DTOs
     */
    List<FriendshipDto> getAllFriendships();

    /**
     * Create a friendship between two users
     *
     * @param user1Id User 1 id
     * @param user2Id User 2 id
     * @return Created Friendship DTO
     * @throws UserNotFoundException if either user not found
     */

    /**
     * Get all friendships for a specific user
     *
     * @param userId User id
     * @return List of Friendship DTOs
     * @throws FriendshipNotFoundException if no friendships found for the user
     */
    List<FriendshipDto> getAllFriendshipsByUserId(Long userId);

    /**
     * Create a friendship between two users
     * Not used directly - friendships are created via friendship requests
     * 
     * @param user1Id User 1 id
     * @param user2Id User 2 id
     * @return Created Friendship DTO
     * @throws UserNotFoundException if either user not found
     */
    FriendshipDto createFriendship(Long user1Id, Long user2Id);

    /**
     * Remove a friendship by its id
     * 
     * @param friendshipId Friendship id
     * @throws FriendshipNotFoundException if friendship not found
     */
    void removeFriendship(Long friendshipId);

    /**
     * Send a friendship request from one user to another
     *
     * @param requestDto DTO containing requester and addressee IDs
     * @return Created FriendshipRequest DTO
     */
    FriendshipRequestDto sendFriendshipRequest(Long requesterId, Long addresseeId);

    /**
     * Get all pending friendship requests for a user
     *
     * @param userId ID of the user
     * @return List of pending FriendshipRequest DTOs
     */
    List<FriendshipRequestDto> getPendingRequestsForUser(Long userId);

    /**
     * Accept a friendship request
     *
     * @param requestId ID of the friendship request to accept
     * @return Accepted FriendshipRequest DTO
     * @throws FriendshipRequestNotFoundException if friendship request not found
     *                                            the request
     */
    FriendshipRequestDto acceptFriendshipRequest(Long requestId);

    /**
     * Decline a friendship request
     *
     * @param requestId ID of the friendship request to decline
     * @return Declined FriendshipRequest DTO
     * @throws FriendshipRequestNotFoundException if friendship request not found
     */
    FriendshipRequestDto declineFriendshipRequest(Long requestId);

    /**
     * Cancel a sent friendship request
     *
     * @param requestId ID of the friendship request to cancel
     */
    void cancelFriendshipRequest(Long requestId);

}