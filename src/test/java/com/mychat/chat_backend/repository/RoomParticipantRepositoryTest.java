package com.mychat.chat_backend.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.mychat.chat_backend.exception.RoomParticipantNotFoundException;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
/**
 * Tests for RoomParticipantRepository.
 */
class RoomParticipantRepositoryTest {

        @Autowired
        private RoomParticipantRepository roomParticipantRepository;

        @Autowired
        private TestEntityManager entityManager;

        private Room room1;
        private Room room2;
        private User participant1;
        private User participant2;

        @BeforeEach
        void setUp() {

                // Create and persist Users
                participant1 = new User.Builder()
                                .username("alice")
                                .password("password1")
                                .email("alice@example.com")
                                .build();
                participant2 = new User.Builder()
                                .username("bob")
                                .password("password2")
                                .email("bob@example.com")
                                .build();

                participant1 = entityManager.persistAndFlush(participant1);
                participant2 = entityManager.persistAndFlush(participant2);

                // Create and persist Rooms
                room1 = new Room.Builder()
                                .description("General Chat")
                                .isPrivate(false)
                                .build();
                room2 = new Room.Builder()
                                .description("Private Chat")
                                .isPrivate(true)
                                .build();

                room1 = entityManager.persistAndFlush(room1);
                room2 = entityManager.persistAndFlush(room2);

        }

        @Test
        void testFindAllByRoom_ShouldReturnAllRoomParticipants() {
                // Given
                RoomParticipant roomParticipant1 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(participant1)
                                .role(ParticipantRole.OWNER)
                                .build();
                RoomParticipant roomParticipant2 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(participant2)
                                .role(ParticipantRole.MEMBER)
                                .build();
                RoomParticipant roomParticipant3 = new RoomParticipant.Builder()
                                .room(room2)
                                .user(participant2)
                                .role(ParticipantRole.MEMBER)
                                .build();
                entityManager.persistAndFlush(roomParticipant1);
                entityManager.persistAndFlush(roomParticipant2);
                entityManager.persistAndFlush(roomParticipant3);

                // When
                List<RoomParticipant> participantsInRoom1 = roomParticipantRepository.findAllByRoom(room1);

                // Then
                Assertions.assertThat(participantsInRoom1).hasSize(2)
                                .containsAll(List.of(roomParticipant1, roomParticipant2));
        }

        @Test
        void testFindAllByUser_ShouldReturnAllRoomParticipantsForUser() {
                // Given
                RoomParticipant roomParticipant1 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(participant1)
                                .role(ParticipantRole.OWNER)
                                .build();
                RoomParticipant roomParticipant2 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(participant2)
                                .role(ParticipantRole.MEMBER)
                                .build();
                RoomParticipant roomParticipant3 = new RoomParticipant.Builder()
                                .room(room2)
                                .user(participant2)
                                .role(ParticipantRole.MEMBER)
                                .build();
                entityManager.persistAndFlush(roomParticipant1);
                entityManager.persistAndFlush(roomParticipant2);
                entityManager.persistAndFlush(roomParticipant3);

                // When
                List<RoomParticipant> participantsForUser2 = roomParticipantRepository.findAllByUser(participant2);

                // Then
                Assertions.assertThat(participantsForUser2).hasSize(2)
                                .containsAll(List.of(roomParticipant2, roomParticipant3));
        }

        @Test
        void testFindByUserAndRoom_ShouldReturnCorrectRoomParticipant() {
                // Given
                RoomParticipant roomParticipant1 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(participant1)
                                .role(ParticipantRole.OWNER)
                                .build();
                entityManager.persistAndFlush(roomParticipant1);

                // When
                RoomParticipant fetchedParticipant = roomParticipantRepository
                                .findByUserAndRoom(participant1, room1)
                                .orElseThrow(RoomParticipantNotFoundException::new);

                // Then
                Assertions.assertThat(fetchedParticipant).isNotNull();
                Assertions.assertThat(fetchedParticipant.getId()).isEqualTo(roomParticipant1.getId());
        }

        @Test
        void testFindByUserAndRoom_ShouldReturnEmptyWhenNotFound() {
                // When
                var result = roomParticipantRepository.findByUserAndRoom(participant1, room1);

                // Then
                Assertions.assertThat(result).isEmpty();
        }

        @Test
        void testFindAllByRole_ShouldReturnAllRoomParticipantsWithGivenRole() {
                // Given
                RoomParticipant roomParticipant1 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(participant1)
                                .role(ParticipantRole.OWNER)
                                .build();
                RoomParticipant roomParticipant2 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(participant2)
                                .role(ParticipantRole.MEMBER)
                                .build();
                RoomParticipant roomParticipant3 = new RoomParticipant.Builder()
                                .room(room2)
                                .user(participant2)
                                .role(ParticipantRole.MEMBER)
                                .build();

                roomParticipant1 = entityManager.persistAndFlush(roomParticipant1);
                roomParticipant2 = entityManager.persistAndFlush(roomParticipant2);
                roomParticipant3 = entityManager.persistAndFlush(roomParticipant3);

                // When
                List<RoomParticipant> members = roomParticipantRepository.findAllByRole(ParticipantRole.MEMBER);

                // Then
                Assertions.assertThat(members).hasSize(2)
                                .containsAll(List.of(roomParticipant2, roomParticipant3));
        }

        @Test
        void testFindAllByRoleAndRoom_ShouldReturnAllRoomParticipantsWithGivenRoleInRoom() {
                // Given
                RoomParticipant roomParticipant1 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(participant1)
                                .role(ParticipantRole.OWNER)
                                .build();
                RoomParticipant roomParticipant2 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(participant2)
                                .role(ParticipantRole.MEMBER)
                                .build();
                RoomParticipant roomParticipant3 = new RoomParticipant.Builder()
                                .room(room2)
                                .user(participant2)
                                .role(ParticipantRole.MEMBER)
                                .build();
                roomParticipant1 = entityManager.persistAndFlush(roomParticipant1);
                roomParticipant2 = entityManager.persistAndFlush(roomParticipant2);
                roomParticipant3 = entityManager.persistAndFlush(roomParticipant3);
                // When
                List<RoomParticipant> membersInRoom1 = roomParticipantRepository
                                .findAllByRoleAndRoom(ParticipantRole.MEMBER, room1);
                List<RoomParticipant> membersInRoom2 = roomParticipantRepository
                                .findAllByRoleAndRoom(ParticipantRole.MEMBER, room2);

                // Then
                Assertions.assertThat(membersInRoom1).hasSize(1)
                                .contains(roomParticipant2);
                Assertions.assertThat(membersInRoom2).hasSize(1)
                                .contains(roomParticipant3);
        }
}
