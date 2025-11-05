package com.mychat.chat_backend.repository;

import java.util.Optional;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
/**
 * Test class for UserRepository
 */
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        // Initialize test data or any required setup
    }

    @Test
    void testFindByRoomParticipationsRoomId_ShouldReturnCorrectUserInRoom() {
        // Given
        User user = new User.Builder()
                .username("testuser")
                .password("password123")
                .email("testuser@example.com")
                .build();
        User user2 = new User.Builder()
                .username("anotheruser")
                .password("password456")
                .email("anotheruser@example.com")
                .build();
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(user2);

        Room room = new Room.Builder()
                .description("Test Room")
                .isPrivate(false)
                .build();
        entityManager.persistAndFlush(room);

        RoomParticipant participant = new RoomParticipant.Builder()
                .room(room)
                .user(user)
                .role(ParticipantRole.MEMBER)
                .build();
        RoomParticipant participant2 = new RoomParticipant.Builder()
                .room(room)
                .user(user2)
                .role(ParticipantRole.MEMBER)
                .build();
        entityManager.persistAndFlush(participant2);
        entityManager.persistAndFlush(participant);

        // When
        List<User> foundUsers = userRepository.findAllByRoomParticipationsRoomId(room.getId());

        // Then
        Assertions.assertThat(foundUsers).isNotEmpty().hasSize(2).extracting(User::getId)
                .containsExactlyInAnyOrder(user.getId(), user2.getId());
    }

    @Test
    void testFindUserByUsername_ShouldReturnCorrectUser() {
        // Given
        User user = new User.Builder()
                .username("uniqueuser")
                .password("securepassword")
                .email("uniqueuser@example.com")
                .build();
        entityManager.persistAndFlush(user);

        // When
        Optional<User> foundUser = userRepository.findByUsername("uniqueuser");
        // Then
        Assertions.assertThat(foundUser).isPresent().get().extracting(User::getEmail)
                .isEqualTo("uniqueuser@example.com");
    }

    @Test
    void testFindUserByEmail_ShouldReturnCorrectUser() {
        // Given
        User user = new User.Builder()
                .username("emailuser")
                .password("emailpassword")
                .email("emailuser@example.com")
                .build();
        entityManager.persistAndFlush(user);

        // When
        Optional<User> foundUser = userRepository.findByEmail("emailuser@example.com");

        // Then
        Assertions.assertThat(foundUser).isPresent().get().extracting(User::getUsername)
                .isEqualTo("emailuser");
    }

    @Test
    void testFindAllByIsOnline_ShouldReturnUsersWithGivenOnlineStatus() {
        // Given
        User onlineUser1 = new User.Builder()
                .username("onlineuser1")
                .password("password1")
                .email("onlineuser1@example.com")
                .build();
        User onlineUser2 = new User.Builder()
                .username("onlineuser2")
                .password("password2")
                .email("onlineuser2@example.com")
                .build();

        onlineUser1.setIsOnline(true);
        onlineUser2.setIsOnline(true);
        entityManager.persistAndFlush(onlineUser1);
        entityManager.persistAndFlush(onlineUser2);

        // When
        List<User> foundUsers = userRepository.findAllByIsOnline(true);

        // Then
        Assertions.assertThat(foundUsers).isNotEmpty().hasSize(2).extracting(User::getId)
                .containsExactlyInAnyOrder(onlineUser1.getId(), onlineUser2.getId());
    }

}
