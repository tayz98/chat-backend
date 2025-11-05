package com.mychat.chat_backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mychat.chat_backend.model.friendship.Friendship;

/**
 * Repository interface for managing Friendship entities.
 */
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

        /**
         * Finds a friendship between two users.
         * 
         * @param userId      the first user ID
         * @param otherUserId the second user ID
         * @return an Optional containing the Friendship if found, or empty if not found
         */
        @Query("SELECT f FROM Friendship f WHERE " +
                        "(f.user.id = :userId AND f.otherUser.id = :otherUserId) OR " +
                        "(f.user.id = :otherUserId AND f.otherUser.id = :userId)")
        Optional<Friendship> findByUserIds(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

        /**
         * Finds all friendships for a given user.
         * 
         * @param userId the user ID
         * @return a list of Friendship entities involving the user
         */
        @Query("SELECT f FROM Friendship f WHERE f.user.id = :userId OR f.otherUser.id = :userId")
        List<Friendship> findAllByUserId(@Param("userId") Long userId);

        /**
         * Finds all friend IDs for a given user.
         * 
         * @param userId the user ID
         * @return a list of friend IDs
         */
        @Query("SELECT CASE WHEN f.user.id = :userId THEN f.otherUser.id ELSE f.user.id END " +
                        "FROM Friendship f WHERE f.user.id = :userId OR f.otherUser.id = :userId")
        List<Long> findAllFriendIdsByUser(@Param("userId") Long userId);

        /**
         * Checks if two users are friends.
         * 
         * @param user1 the first user
         * @param user2 the second user
         * @return true if they are friends, false otherwise
         **/
        @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
                        "FROM Friendship f WHERE " +
                        "(f.user.id = :userId1 AND f.otherUser.id = :userId2) OR " +
                        "(f.user.id = :userId2 AND f.otherUser.id = :userId1)")

        boolean areFriends(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}