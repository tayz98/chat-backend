package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.user.*;
import com.mychat.chat_backend.model.User;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
/**
 * Mapper class for converting between User entities and User DTOs.
 */
public class UserMapper {

    private UserMapper() {
    }

    /**
     * Converts a User entity to a UserDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserDto
     */
    public static UserDto toUserDto(User user) {
        Long id = user.getId();
        String username = user.getUsername();
        String avatarUrl = user.getAvatarUrl();
        UserSummaryDto summary = new UserSummaryDto(id, username, avatarUrl);
        String email = user.getEmail();

        Boolean isOnline = user.getIsOnline();
        Boolean isAdmin = user.getIsAdmin();
        Instant lastLogin = user.getLastLogin();
        // Instant lastLogout = user.getLastLogout();
        Instant created = user.getCreatedOn();
        // Set<Long> currentRooms =
        // user.getCurrentRooms().stream().map(Room::getId).collect(Collectors.toSet());
        return new UserDto(summary, email, created, lastLogin, isOnline, isAdmin);
    }

    /**
     * Converts a UserCreationDto to a User entity.
     * 
     * @param userDto the UserCreationDto to convert
     * @return the corresponding User entity
     */
    public static User toUser(UserCreationDto userDto) {
        return new User.Builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .isAdmin(userDto.isAdmin())
                .build();
    }

    /**
     * Updates an existing User entity with values from a UserUpdateDto.
     *
     * @param userUpdateDto   the UserUpdateDto containing updated values
     * @param userToBeUpdated the User entity to be updated
     * @return the updated User entity
     */
    public static User updatedUser(UserUpdateDto userUpdateDto, User userToBeUpdated) {
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
        if (userUpdateDto.getEmail() != null) {
            userToBeUpdated.setEmail(userUpdateDto.getEmail());
        }
        return userToBeUpdated;
    }
}
