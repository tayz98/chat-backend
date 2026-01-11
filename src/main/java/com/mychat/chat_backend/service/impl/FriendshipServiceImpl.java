package com.mychat.chat_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mychat.chat_backend.dto.friendship.*;
import com.mychat.chat_backend.dto.user.UserDto;
import com.mychat.chat_backend.exception.*;
import com.mychat.chat_backend.mapper.FriendshipMapper;
import com.mychat.chat_backend.mapper.UserMapper;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.FriendshipStatus;
import com.mychat.chat_backend.model.friendship.*;
import com.mychat.chat_backend.repository.FriendshipRepository;
import com.mychat.chat_backend.repository.FriendshipRequestRepository;
import com.mychat.chat_backend.repository.UserRepository;
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
    public List<UserDto> getFriends(Long userId) {
        List<Long> friendIds = friendshipRepository.findAllFriendIdsByUser(userId);
        return friendIds.stream()
                .map(id -> userRepository.findById(id).orElseThrow(UserNotFoundException::new))
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Override
    public List<FriendshipDto> getAllFriendships() {
        List<Friendship> friendships = friendshipRepository.findAll();
        return friendships.stream().map(FriendshipMapper::toFriendshipDto).toList();
    }

    @Override
    public List<FriendshipDto> getAllFriendshipsByUserId(Long userId) {
        List<Friendship> friendships = friendshipRepository.findAllByUserId(userId);
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
        if (friendshipRepository.areFriends(fromUser.getId(), toUser.getId())) {
            throw new IllegalArgumentException("Friendship already exists between the users.");
        }
        FriendshipRequest friendshipRequest = FriendshipRequest.createFriendshipRequest(fromUser, toUser);
        friendshipRequestRepository.save(friendshipRequest);
        return FriendshipMapper.toFriendshipRequestDto(friendshipRequest);
    }

    @Override
    public List<FriendshipRequestDto> getPendingRequestsForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        List<FriendshipRequest> pendingRequests = friendshipRequestRepository
                .findAllByUserId(userId).stream()
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
    public FriendshipRequestDto acceptFriendshipRequest(Long requestId) {
        FriendshipRequest request = friendshipRequestRepository.findById(requestId)
                .orElseThrow(FriendshipRequestNotFoundException::new);

        Friendship newFriendship = request.acceptRequest();
        friendshipRequestRepository.save(request);
        friendshipRepository.save(newFriendship);
        return FriendshipMapper.toFriendshipRequestDto(request);
    }

    @Override
    public FriendshipRequestDto declineFriendshipRequest(Long requestId) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(requestId)
                .orElseThrow(FriendshipRequestNotFoundException::new);
        friendshipRequest.declineRequest();
        friendshipRequestRepository.save(friendshipRequest);
        return FriendshipMapper.toFriendshipRequestDto(friendshipRequest);
    }

}
