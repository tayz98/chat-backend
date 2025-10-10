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

import java.util.List;
import java.util.Optional;

@Service
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
    public UserDto getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
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
    public List<UserDto> getUsersOfRoom(long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        return room.getParticipants().stream().map(UserMapper::toUserDto).toList();
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
    public UserDto updateUser(UserUpdateDto userDto, long userId) {
        User user = userRepository.findById(userId).orElseThrow((UserNotFoundException::new));
        List<Long> roomIds = userDto.getCurrentRooms();
        List<Room> newRooms = roomIds.stream().map(roomRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();
        User updatedUser = UserMapper.updatedUser(userDto, user, newRooms);
        updatedUser.setUpdatedOn();
        return UserMapper.toUserDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow((UserNotFoundException::new));
        userRepository.delete(user);
    }
}
