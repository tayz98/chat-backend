package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.user.UserCreationDto;
import com.mychat.chat_backend.dto.user.UserDto;
import com.mychat.chat_backend.dto.user.UserUpdateDto;

public interface UserService {

    UserDto getUserById(long userId);

    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);

    UserDto createUser(UserCreationDto userDto);

    UserDto updateUser(UserUpdateDto userDto);

    void deleteUser(long userId);
}
