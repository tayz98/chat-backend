package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.user.UserCreationDto;
import com.mychat.chat_backend.dto.user.UserDto;
import com.mychat.chat_backend.dto.user.UserUpdateDto;
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
        User user = userRepository.findById(userId).orElseThrow();
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto createUser(UserCreationDto userDto) {
        User newUser = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.save(newUser));
    }

    @Override
    public UserDto updateUser(UserUpdateDto userDto) {
        User user = userRepository.findById(userDto.getUserId()).orElseThrow();
        List<Long> roomIds = userDto.getCurrentRooms();
        List<Room> rooms = roomIds.stream().map(roomRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();
        User updatedUser = UserMapper.updatedUser(userDto, user, rooms);
        return UserMapper.toUserDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        userRepository.delete(user);
    }
}
