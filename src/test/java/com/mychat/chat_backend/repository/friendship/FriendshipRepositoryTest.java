package com.mychat.chat_backend.repository.friendship;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.friendship.Friendship;
import com.mychat.chat_backend.repository.FriendshipRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
/**
 * Test class for FriendshipRepository
 */
class FriendshipRepositoryTest {

        @Autowired
        private FriendshipRepository friendshipRepository;

        @Autowired
        private TestEntityManager entityManager;

        User user1;
        User user2;
        User user3;
        User user4;
        User user5;

        @BeforeEach
        void setUp() {
                // Given:

                // Create users
                user1 = new User.Builder()
                                .username("user1")
                                .password("password")
                                .email("user1@example.com")
                                .build();

                user2 = new User.Builder()
                                .username("user2")
                                .password("password")
                                .email("user2@example.com")
                                .build();

                user3 = new User.Builder()
                                .username("user3")
                                .password("password")
                                .email("user3@example.com")
                                .build();

                user4 = new User.Builder()
                                .username("user4")
                                .password("password")
                                .email("user4@example.com")
                                .build();

                user5 = new User.Builder()
                                .username("user5")
                                .password("password")
                                .email("user5@example.com")
                                .build();

                // Persist users

                user1 = entityManager.persist(user1);
                user2 = entityManager.persist(user2);
                user3 = entityManager.persist(user3);
                user4 = entityManager.persist(user4);
                user5 = entityManager.persist(user5);

                // Create and persist friendships
                friendshipRepository.save(Friendship.createFriendship(user1, user2));
                friendshipRepository.save(Friendship.createFriendship(user1, user3));
                friendshipRepository.save(Friendship.createFriendship(user2, user4));
                friendshipRepository.save(Friendship.createFriendship(user3, user5));

                entityManager.flush();
        }

        @Test
        void testFindByUserIds_ShouldReturnFriendshipBetweenTwoUsers() {
                // When
                Optional<Friendship> friendshipOpt = friendshipRepository.findByUserIds(user1.getId(), user2.getId());

                // Then
                Assertions.assertThat(friendshipOpt)
                                .isPresent()
                                .get()
                                .satisfies(friendship -> {
                                        boolean usersMatch = (friendship.getUser().getId().equals(user1.getId()) &&
                                                        friendship.getOtherUser().getId().equals(user2.getId()))
                                                        ||
                                                        (friendship.getUser().getId().equals(user2.getId()) &&
                                                                        friendship.getOtherUser().getId()
                                                                                        .equals(user1.getId()));

                                        Assertions.assertThat(usersMatch).isTrue();
                                });

        }

        @Test
        void testFindAllByUserId_ShouldReturnAllFriendshipsForUser() {
                // When
                var friendships = friendshipRepository.findAllByUserId(user3.getId());

                // Then
                Assertions.assertThat(friendships).hasSize(2).extracting(f -> f.getFriendOf(user3))
                                .containsExactlyInAnyOrder(user1, user5);
        }

        @Test
        void testFindAllFriendIdsByUser_ShouldReturnAllFriendIdsForUser() {
                // When
                var friendIds = friendshipRepository.findAllFriendIdsByUser(user2.getId());

                // Then
                Assertions.assertThat(friendIds).hasSize(2)
                                .containsExactlyInAnyOrder(user1.getId(), user4.getId());
        }

        @Test
        void testAreFriends_ShouldReturnTrueIfTheyAreFriends() {
                boolean result = friendshipRepository.areFriends(user1.getId(), user2.getId());

                // Then
                Assertions.assertThat(result).isTrue();
        }
}
