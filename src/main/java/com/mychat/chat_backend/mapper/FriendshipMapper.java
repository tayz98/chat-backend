package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.friendship.*;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.FriendshipStatus;
import com.mychat.chat_backend.model.friendship.Friendship;
import com.mychat.chat_backend.model.friendship.FriendshipRequest;

import java.time.Instant;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
/**
 * Mapper class for converting between Friendship entities and DTOs.
 */
public class FriendshipMapper {

    private FriendshipMapper() {
    }

    /**
     * Converts a Friendship entity to a FriendshipDto.
     *
     * @param friendship the Friendship entity to convert
     * @param user       the User who owns the friendship
     * @param friend     the User who is the friend
     * @return the corresponding FriendshipDto
     */
    public static FriendshipDto toFriendshipDto(Friendship friendship) {
        Long id = friendship.getId();
        Long userId = friendship.getUser().getId();
        Long friendId = friendship.getOtherUser().getId();
        String friendName = friendship.getOtherUser().getUsername();
        Instant establishedOn = friendship.getEstablishedOn();
        return new FriendshipDto(id, userId, friendId, friendName, establishedOn);
    }

    /**
     * Converts a FriendshipDto to a Friendship entity.
     *
     * @param friendshipDto the FriendshipDto to convert
     * @param firstUser     one of the Users in the friendship
     * @param secondUser    the other User in the friendship
     * @return the corresponding Friendship entity
     * @throws IllegalArgumentException if the userId in the DTO does not match
     *                                  either provided User
     */
    public static Friendship toFriendship(FriendshipDto friendshipDto, User firstUser, User secondUser) {
        if (!Objects.equals(friendshipDto.getUserId(), firstUser.getId()) &&
                !Objects.equals(friendshipDto.getUserId(), secondUser.getId())) {
            throw new IllegalArgumentException("FriendshipDto userId does not match either provided User");
        }
        return Friendship.createFriendship(firstUser, secondUser);
    }

    /**
     * Converts a FriendshipRequest entity to a FriendshipRequestDto.
     *
     * @param friendshipRequest the FriendshipRequest entity to convert
     * @return the corresponding FriendshipRequestDto
     */
    public static FriendshipRequestDto toFriendshipRequestDto(FriendshipRequest friendshipRequest) {
        Long id = friendshipRequest.getId();
        Long requesterId = friendshipRequest.getRequester().getId();
        Long addresseeId = friendshipRequest.getAddressee().getId();
        String requesterName = friendshipRequest.getRequester().getUsername();
        String addresseeName = friendshipRequest.getAddressee().getUsername();
        Instant requestedAt = friendshipRequest.getRequestedAt();
        Instant respondedAt = friendshipRequest.getRespondedAt();
        FriendshipStatus statusOfRequest = friendshipRequest.getStatusOfRequest();
        return new FriendshipRequestDto(id, requesterId, addresseeId, requesterName,
                addresseeName, requestedAt, respondedAt, statusOfRequest);
    }

    /**
     * Converts a FriendshipRequestDto to a FriendshipRequest entity.
     *
     * @param friendshipRequestDto the FriendshipRequestDto to convert
     * @param requester            the User who sent the request
     * @param addressee            the User who received the request
     * @return the corresponding FriendshipRequest entity
     * @throws IllegalArgumentException if the requesterId or addresseeId in the DTO
     *                                  does not match the provided Users
     */
    public static FriendshipRequest toFriendshipRequest(FriendshipRequestDto friendshipRequestDto, User requester,
            User addressee) {
        Long id = friendshipRequestDto.getId();
        if (!Objects.equals(friendshipRequestDto.getRequesterId(), requester.getId()) ||
                !Objects.equals(friendshipRequestDto.getAddresseeId(), addressee.getId())) {
            throw new IllegalArgumentException(
                    "FriendshipRequestDto requesterId or addresseeId does not match provided Users");
        }
        Instant requestedAt = friendshipRequestDto.getRequestedAt();
        Instant respondedAt = friendshipRequestDto.getRespondedAt();
        FriendshipStatus statusOfRequest = friendshipRequestDto.getStatusOfRequest();
        return new FriendshipRequest(id, requester, addressee, requestedAt, respondedAt, statusOfRequest);

    }
}
