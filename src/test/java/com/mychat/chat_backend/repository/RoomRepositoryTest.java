package com.mychat.chat_backend.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.mychat.chat_backend.model.Message;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
/**
 * Tests for RoomRepository.
 */
class RoomRepositoryTest {

        @Autowired
        private RoomRepository roomRepository;

        @Autowired
        private TestEntityManager entityManager;

        @BeforeEach
        void setUp() {

        }

        @Test
        void testFindByParticipantsContainingId_ShouldReturnCorrectRoomWhereUserIsParticipant() {
                // Given
                User user = new User.Builder()
                                .username("testuser")
                                .password("password")
                                .email("testuser@example.com")
                                .build();
                User user2 = new User.Builder()
                                .username("otheruser")
                                .password("password")
                                .email("otheruser@example.com")
                                .build();
                user = entityManager.persistAndFlush(user);
                user2 = entityManager.persistAndFlush(user2);

                Room room = new Room.Builder()
                                .description("Test Room")
                                .isPrivate(false)
                                .build();
                Room room2 = new Room.Builder()
                                .description("Test Room2")
                                .isPrivate(true)
                                .build();
                room = entityManager.persistAndFlush(room);
                room2 = entityManager.persistAndFlush(room2);

                RoomParticipant roomParticipant = new RoomParticipant.Builder()
                                .room(room)
                                .user(user)
                                .role(ParticipantRole.MEMBER)
                                .build();
                RoomParticipant roomParticipant2 = new RoomParticipant.Builder()
                                .room(room2)
                                .user(user2)
                                .role(ParticipantRole.OWNER)
                                .build();
                entityManager.persistAndFlush(roomParticipant);
                entityManager.persistAndFlush(roomParticipant2);

                // When
                List<Room> result = roomRepository.findAllByParticipantsUserId(user.getId());

                // Then
                Assertions.assertThat(result).singleElement().extracting(Room::getId).isEqualTo(room.getId());
        }

        @Test
        void testFindByIsPrivate_ShouldReturnPrivateRooms() {
                // Given
                Room privateRoom = new Room.Builder()
                                .description("Private Room")
                                .isPrivate(true)
                                .build();
                Room publicRoom = new Room.Builder()
                                .description("Public Room")
                                .isPrivate(false)
                                .build();
                entityManager.persistAndFlush(privateRoom);
                entityManager.persistAndFlush(publicRoom);

                // When
                var privateRooms = roomRepository.findAllByIsPrivate(true);

                // Then
                Assertions.assertThat(privateRooms).singleElement().extracting(Room::getId)
                                .isEqualTo(privateRoom.getId());
        }

        @Test
        void testFindByDescription_ShouldReturnRoomsContainingKeyword() {
                // Given
                Room room1 = new Room.Builder()
                                .description("Java Chat Room")
                                .isPrivate(false)
                                .build();
                Room room2 = new Room.Builder()
                                .description("Python Discussion Room")
                                .isPrivate(false)
                                .build();
                entityManager.persistAndFlush(room1);
                entityManager.persistAndFlush(room2);

                // When
                var rooms = roomRepository.findAllByDescriptionContaining("Chat");

                // Then
                Assertions.assertThat(rooms).hasSize(1).extracting(Room::getDescription)
                                .matches(descriptions -> descriptions.contains("Java Chat Room"));
        }

        @Test
        void testFindByMessagesId_ShouldReturnCorrectRoomContainingMessage() {
                // Given

                // Create and persist rooms
                Room room = new Room.Builder()
                                .description("Message Room")
                                .isPrivate(false)
                                .build();
                Room room2 = new Room.Builder()
                                .description("Another Room")
                                .isPrivate(false)
                                .build();
                room = entityManager.persistAndFlush(room);
                room2 = entityManager.persistAndFlush(room2);

                // Create and persist users and messages
                User user = new User.Builder()
                                .username("testuser")
                                .password("password")
                                .email("testuser@example.com")
                                .build();
                User user2 = new User.Builder()
                                .username("otheruser")
                                .password("password")
                                .email("otheruser@example.com")
                                .build();
                user = entityManager.persistAndFlush(user);
                user2 = entityManager.persistAndFlush(user2);

                Message message1 = new Message.Builder()
                                .content("Hello World")
                                .room(room)
                                .user(user2)
                                .build();
                Message message2 = new Message.Builder()
                                .content("Another Message")
                                .room(room2)
                                .user(user)
                                .build();

                message1 = entityManager.persistAndFlush(message1);
                entityManager.persistAndFlush(message2);

                // When
                Optional<Room> foundRoom = roomRepository.findByMessagesId(message1.getId());

                // Then
                Assertions.assertThat(foundRoom)
                                .isPresent()
                                .get()
                                .extracting(Room::getId)
                                .isEqualTo(room.getId());
        }

}
