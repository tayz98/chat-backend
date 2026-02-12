package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.user.UserCreationDto;
import com.mychat.chat_backend.dto.user.UserDto;
import com.mychat.chat_backend.dto.user.UserUpdateDto;
import com.mychat.chat_backend.exception.RoomNotFoundException;
import com.mychat.chat_backend.exception.UserNotFoundException;
import com.mychat.chat_backend.mapper.UserMapper;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoomRepository roomRepository;

    @SuppressWarnings("unused")
    private UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserMapper.toUserDto(user);
    }

    @Override
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::toUserDto);
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toUserDto);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toUserDto).toList();
    }

    @Override
    public List<UserDto> getUsersOfRoomWithRoomRepository(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        return room.getParticipantUsers().stream().map(UserMapper::toUserDto).toList();
    }

    @Override
    public List<UserDto> getUsersByRoomWithUserRepository(Long roomId) {
        List<User> users = userRepository.findAllByRoomParticipationsRoomId(roomId);
        return users.stream().map(UserMapper::toUserDto).toList();
    }

    @Override
    public List<UserDto> getUsersByOnlineStatus(Boolean isOnline) {
        List<User> users = userRepository.findAllByIsOnline(isOnline);
        return users.stream().map(UserMapper::toUserDto).toList();
    }

    @Override
    public UserDto createUser(UserCreationDto userDto) {
        User newUser = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.save(newUser));
    }

    @Override
    public UserDto updateUser(UserUpdateDto userDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow((UserNotFoundException::new));
        User updatedUser = UserMapper.updatedUser(userDto, user);
        updatedUser.setUpdatedOn();
        return UserMapper.toUserDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow((UserNotFoundException::new));
        userRepository.delete(user);
    }

    @Override
    public void setOnlineStatus(Long userId, Boolean isOnline) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setIsOnline(isOnline);
        if (Boolean.TRUE.equals(isOnline)) {
            user.setLastLogin(Instant.now());
        } else {
            user.setLastLogout(Instant.now());
        }
        userRepository.save(user);
    }
}
