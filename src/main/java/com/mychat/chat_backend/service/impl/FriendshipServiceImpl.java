package com.mychat.chat_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mychat.chat_backend.dto.friendship.*;
import com.mychat.chat_backend.exception.*;
import com.mychat.chat_backend.mapper.FriendshipMapper;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.FriendshipStatus;
import com.mychat.chat_backend.model.friendship.*;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.repository.friendship.*;
import com.mychat.chat_backend.service.FriendshipService;

import jakarta.transaction.Transactional;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private FriendshipRepository friendshipRepository;
    private FriendshipRequestRepository friendshipRequestRepository;
    private UserRepository userRepository;

    @SuppressWarnings("unused")
    private FriendshipServiceImpl() {
    }

    @Autowired
    public FriendshipServiceImpl(FriendshipRepository friendshipRepository,
            FriendshipRequestRepository friendshipRequestRepository,
            UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.friendshipRequestRepository = friendshipRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FriendshipDto getFriendshipById(Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(FriendshipNotFoundException::new);
        return FriendshipMapper.toFriendshipDto(friendship);
    }

    @Override
    public List<User> getFriends(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Long> friendIds = friendshipRepository.findAllFriendIdsByUser(user);
        return friendIds.stream().map(id -> userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new)).toList();
    }

    @Override
    public List<FriendshipDto> getAllFriendships() {
        List<Friendship> friendships = friendshipRepository.findAll();
        return friendships.stream().map(FriendshipMapper::toFriendshipDto).toList();
    }

    @Override
    public List<FriendshipDto> getAllFriendshipsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Friendship> friendships = friendshipRepository.findAllByUser(user);
        if (friendships.isEmpty()) {
            throw new FriendshipNotFoundException();
        }
        return friendships.stream().map(FriendshipMapper::toFriendshipDto).toList();
    }

    @Override
    public FriendshipDto createFriendship(Long user1Id, Long user2Id) {
        User user = userRepository.findById(user1Id).orElseThrow(UserNotFoundException::new);
        User friend = userRepository.findById(user2Id).orElseThrow(UserNotFoundException::new);
        Friendship friendship = Friendship.createFriendship(user, friend);
        friendshipRepository.save(friendship);
        return FriendshipMapper.toFriendshipDto(friendship);
    }

    @Override
    public void removeFriendship(Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(FriendshipNotFoundException::new);
        friendshipRepository.delete(friendship);
    }

    @Override
    public FriendshipRequestDto sendFriendshipRequest(Long requesterId, Long addresseeId) {
        User fromUser = userRepository.findById(requesterId).orElseThrow(UserNotFoundException::new);
        User toUser = userRepository.findById(addresseeId).orElseThrow(UserNotFoundException::new);
        FriendshipRequest friendshipRequest = FriendshipRequest.createFriendshipRequest(fromUser, toUser);
        friendshipRequestRepository.save(friendshipRequest);
        return FriendshipMapper.toFriendshipRequestDto(friendshipRequest);
    }

    @Override
    public List<FriendshipRequestDto> getPendingRequestsForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<FriendshipRequest> pendingRequests = friendshipRequestRepository
                .findAllByUser(user).stream()
                .filter(request -> request.getStatusOfRequest() == FriendshipStatus.PENDING)
                .toList();
        return pendingRequests.stream()
                .map(FriendshipMapper::toFriendshipRequestDto)
                .toList();
    }

    @Transactional
    @Override
    public void cancelFriendshipRequest(Long requestId) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(requestId)
                .orElseThrow(FriendshipRequestNotFoundException::new);
        friendshipRequestRepository.delete(friendshipRequest);
    }

    @Override
    public FriendshipRequestDto acceptFriendshipRequest(Long requestId, Long addresseeId) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(requestId)
                .orElseThrow(FriendshipRequestNotFoundException::new);
        if (!friendshipRequest.getAddressee().getId().equals(addresseeId)) {
            throw new IllegalArgumentException("Addressee does not match the request.");
        }
        Friendship newFriendship = friendshipRequest.acceptRequest();
        friendshipRequestRepository.save(friendshipRequest);
        friendshipRepository.save(newFriendship);
        return FriendshipMapper.toFriendshipRequestDto(friendshipRequest);
    }

    @Override
    public FriendshipRequestDto declineFriendshipRequest(Long requestId, Long addresseeId) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(requestId)
                .orElseThrow(FriendshipRequestNotFoundException::new);
        if (!friendshipRequest.getAddressee().getId().equals(addresseeId)) {
            throw new IllegalArgumentException("Addressee does not match the request.");
        }
        friendshipRequest.declineRequest();
        friendshipRequestRepository.save(friendshipRequest);
        return FriendshipMapper.toFriendshipRequestDto(friendshipRequest);
    }

}
