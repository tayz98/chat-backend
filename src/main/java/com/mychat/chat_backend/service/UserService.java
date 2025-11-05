package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.user.*;
import com.mychat.chat_backend.exception.*;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Get a user by its id
     *
     * @param userId User id
     * @return User DTO
     * @throws UserNotFoundException if the user is not found
     */
    UserDto getUserById(Long userId);

    /**
     * Get a user by its username
     *
     * @param username Username
     * @return User DTO
     */
    Optional<UserDto> getUserByUsername(String username);

    /**
     * Get a user by its email
     *
     * @param email Email
     * @return User DTO
     */
    Optional<UserDto> getUserByEmail(String email);

    /**
     * Get all users
     *
     * @return List of User DTOs
     */
    List<UserDto> getAllUsers();

    /**
     * Get all users of a specific room
     *
     * @param roomId Room id
     * @return List of User DTOs
     */
    List<UserDto> getUsersOfRoom(Long roomId);

    /**
     * Get all users by their online status
     *
     * @param isOnline Online status
     * @return List of User DTOs
     */
    List<UserDto> getUsersByOnlineStatus(Boolean isOnline);

    /**
     * Create a new user
     *
     * @param userDto User creation DTO
     * @return Created User DTO
     */
    UserDto createUser(UserCreationDto userDto);

    /**
     * Update an existing user
     *
     * @param userDto User update DTO
     * @param userId  User id
     * @return Updated User DTO
     */
    UserDto updateUser(UserUpdateDto userDto, Long userId);

    /**
     * Get all users by room id
     *
     * @param roomId Room id
     * @return List of User DTOs
     */
    List<UserDto> getUsersByRoomId(Long roomId);

    /**
     * Delete a user
     *
     * @param userId User id
     */
    void deleteUser(Long userId);

    /**
     * Set the online status of a user
     * and update last login/logout timestamps accordingly
     *
     * @param userId   User id
     * @param isOnline Online status
     */
    void setOnlineStatus(Long userId, Boolean isOnline);

}
