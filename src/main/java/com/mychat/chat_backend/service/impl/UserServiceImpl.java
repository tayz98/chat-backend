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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoomRepository roomRepository;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public UserDto getUserById(@NotNull Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByUsername(@NotBlank String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(@Email String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toUserDto).toList();
    }

    @Override
    public List<UserDto> getUsersOfRoom(@NotNull Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        return room.getParticipants().stream().map(UserMapper::toUserDto).toList();
    }

    @Override
    public List<UserDto> getUsersByOnlineStatus(@NotNull Boolean isOnline) {
        List<User> users = userRepository.findAllByIsOnline(isOnline);
        return users.stream().map(UserMapper::toUserDto).toList();
    }

    @Override
    public UserDto createUser(@NotNull UserCreationDto userDto) {
        User newUser = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.save(newUser));
    }

    @Override
    public UserDto updateUser(@NotNull UserUpdateDto userDto, @NotNull Long userId) {
        User user = userRepository.findById(userId).orElseThrow((UserNotFoundException::new));
        Set<Long> roomIds = userDto.getCurrentRooms();
        Set<Room> newRooms = roomIds.stream().map(roomRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
        User updatedUser = UserMapper.updatedUser(userDto, user, newRooms);
        updatedUser.setUpdatedOn();
        return UserMapper.toUserDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(@NotNull Long userId) {
        User user = userRepository.findById(userId).orElseThrow((UserNotFoundException::new));
        userRepository.delete(user);
    }
}
