package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.user.UserCreationDto;
import com.mychat.chat_backend.dto.user.UserDto;
import com.mychat.chat_backend.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(Long userId);

    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    List<UserDto> getUsersOfRoom(Long roomId);

    List<UserDto> getUsersByOnlineStatus(Boolean isOnline);

    UserDto createUser(UserCreationDto userDto);

    UserDto updateUser(UserUpdateDto userDto, Long userId);

    void deleteUser(Long userId);
}
