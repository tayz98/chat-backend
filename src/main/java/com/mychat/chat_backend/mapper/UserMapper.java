package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.user.UserCreationDto;
import com.mychat.chat_backend.dto.user.UserDto;
import com.mychat.chat_backend.dto.user.UserUpdateDto;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        long id = user.getId();
        String username = user.getUsername();
        String email = user.getEmail();
        String avatarUrl = user.getAvatarUrl();
        Boolean isOnline = user.getOnline();
        Boolean isAdmin = user.getAdmin();
        Instant lastLogin = user.getLastLogin();
        Instant lastLogout = user.getLastLogout();
        Instant created = user.getCreated();
        List<Long> currentRooms = user.getCurrentRooms().stream().map(Room::getId).toList();
        return new UserDto(id, username, email, created, lastLogin, lastLogout, avatarUrl, isOnline, isAdmin, currentRooms);
    }

    public static User toUser(UserCreationDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), userDto.isAdmin());
    }

    public static User updatedUser(UserUpdateDto userUpdateDto, User user, List<Room> rooms) {
        if (userUpdateDto.getPassword() != null) {
            user.setPassword(userUpdateDto.getPassword());
        }
        if (userUpdateDto.getAvatarUrl() != null) {
            user.setAvatarUrl(userUpdateDto.getAvatarUrl());
        }
        if (userUpdateDto.getLastLogin() != null) {
            user.setLastLogin(userUpdateDto.getLastLogin());
        }
        if (userUpdateDto.getLastLogout() != null) {
            user.setLastLogout(userUpdateDto.getLastLogout());
        }
        if (userUpdateDto.getAdmin() != null) {
            user.setAdmin(userUpdateDto.getAdmin());
        }
        if (userUpdateDto.getOnline() != null) {
            user.setOnline(userUpdateDto.getOnline());
        }
        if (userUpdateDto.getCurrentRooms() != null) {
            user.setCurrentRooms(rooms);
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        return user;
    }
}
