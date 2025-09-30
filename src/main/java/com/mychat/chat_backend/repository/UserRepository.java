package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User repository
 * Contains methods to access users in the database
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by username
     *
     * @param username
     * @return User with the given username
     */
    User findByUsername(String username);

    /**
     * Find a user by email
     *
     * @param email
     * @return User with the given email
     */
    User findByEmail(String email);

    /**
     * Find all users with a specific online status
     *
     * @param isOnline
     * @return List of users with the given online status
     */
    public List<User> findAllByIsOnline(Boolean isOnline);

    /**
     * Find all users in a specific room
     *
     * @param currentRoomsId
     * @return List of users in the given room
     */
    public List<User> findAllByCurrentRoomsId(Long currentRoomsId);

}
