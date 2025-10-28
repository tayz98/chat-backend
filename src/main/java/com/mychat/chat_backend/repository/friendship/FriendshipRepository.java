package com.mychat.chat_backend.repository.friendship;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.friendship.Friendship;

/**
 * Repository interface for managing Friendship entities.
 */
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

        @Query("SELECT f FROM Friendship f WHERE " +
                        "(f.user = :user AND f.otherUser = :otherUser) OR " +
                        "(f.user = :otherUser AND f.otherUser = :user)")
        /**
         * Finds a friendship between two users.
         * 
         * @param user      the first user
         * @param otherUser the second user
         * @return an Optional containing the Friendship if found, or empty if not found
         */
        Optional<Friendship> findByUserAndOtherUser(User user, User otherUser);

        @Query("SELECT f FROM Friendship f WHERE f.user = :user OR f.otherUser = :user")
        /**
         * Finds all friendships for a given user.
         * 
         * @param user the user
         * @return a list of Friendship entities involving the user
         */
        List<Friendship> findAllByUser(@Param("user") User user);

        @Query("SELECT CASE WHEN f.user = :user THEN f.otherUser.id ELSE f.user.id END " +
                        "FROM Friendship f WHERE f.user = :user OR f.otherUser = :user")
        /**
         * Finds all friend IDs for a given user.
         * 
         * @param user the user
         * @return a list of friend IDs
         */
        List<Long> findAllFriendIdsByUser(@Param("user") User user);

        @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
                        "FROM Friendship f WHERE " +
                        "(f.user = :user1 AND f.otherUser = :user2) OR " +
                        "(f.user = :user2 AND f.otherUser = :user1)")
        /**
         * Checks if two users are friends.
         * 
         * @param user1 the first user
         * @param user2 the second user
         * @return true if they are friends, false otherwise
         */
        boolean areFriends(@Param("user1") User user1, @Param("user2") User user2);
}