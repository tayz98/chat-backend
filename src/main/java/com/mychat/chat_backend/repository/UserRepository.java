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
     * @return User with the given username
     */
    User findByUsername(String username);

    /**
     * Find a user by email
     *
     * @param email Email of the user to be searched for
     * @return User with the given email
     */
    User findByEmail(String email);

    /**
     * Find all users with a specific online status
     *
     * @param isOnline Online status of the user to be searched for
     * @return List of users with the given online status
     */
    List<User> findAllByIsOnline(Boolean isOnline);

    /**
     * Find all users in a specific room
     *
     * @param currentRoomsId Room id of the room to be searched for
     * @return List of users in the given room
     */
    List<User> findAllByCurrentRoomsId(Long currentRoomsId);

}
