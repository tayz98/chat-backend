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

    private UserMapper() {
    }

    public static UserDto toUserDto(User user) {
        long id = user.getId();
        String username = user.getUsername();
        String email = user.getEmail();
        String avatarUrl = user.getAvatarUrl();
        Boolean isOnline = user.getOnline();
        Boolean isAdmin = user.getAdmin();
        Instant lastLogin = user.getLastLogin();
        Instant lastLogout = user.getLastLogout();
        Instant created = user.getCreatedOn();
        List<Long> currentRooms = user.getCurrentRooms().stream().map(Room::getId).toList();
        return new UserDto(id, username, email, created, lastLogin, lastLogout, avatarUrl, isOnline, isAdmin, currentRooms);
    }

    public static User toUser(UserCreationDto userDto) {
        return new User.Builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .isAdmin(userDto.isAdmin())
                .build();
    }

    public static User updatedUser(UserUpdateDto userUpdateDto, User userToBeUpdated, List<Room> rooms) {
        if (userUpdateDto.getPassword() != null) {
            userToBeUpdated.setPassword(userUpdateDto.getPassword());
        }
        if (userUpdateDto.getAvatarUrl() != null) {
            userToBeUpdated.setAvatarUrl(userUpdateDto.getAvatarUrl());
        }
        if (userUpdateDto.getLastLogin() != null) {
            userToBeUpdated.setLastLogin(userUpdateDto.getLastLogin());
        }
        if (userUpdateDto.getLastLogout() != null) {
            userToBeUpdated.setLastLogout(userUpdateDto.getLastLogout());
        }
        if (userUpdateDto.getAdmin() != null) {
            userToBeUpdated.setAdmin(userUpdateDto.getAdmin());
        }
        if (userUpdateDto.getOnline() != null) {
            userToBeUpdated.setOnline(userUpdateDto.getOnline());
        }
        if (userUpdateDto.getCurrentRooms() != null) {
            userToBeUpdated.setCurrentRooms(rooms);
        }

        if (userUpdateDto.getEmail() != null) {
            userToBeUpdated.setEmail(userUpdateDto.getEmail());
        }
        userToBeUpdated.setUpdatedOn();
        return userToBeUpdated;
    }
}
