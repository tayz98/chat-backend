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

import com.mychat.chat_backend.model.Message;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MessageRepositoryTest {

        @Autowired
        private MessageRepository messageRepository;

        @Autowired
        private TestEntityManager entityManager;

        private User user1;
        private User user2;
        private Room room1;
        private Room room2;

        @BeforeEach
        void setUp() {
                // Create and persist users
                user1 = new User.Builder()
                                .username("User1")
                                .password("password1")
                                .email("user1@example.com")
                                .build();
                user2 = new User.Builder()
                                .username("User2")
                                .password("password2")
                                .email("user2@example.com")
                                .build();
                user1 = entityManager.persistAndFlush(user1);
                user2 = entityManager.persistAndFlush(user2);

                // Create and persist rooms
                room1 = new Room.Builder()
                                .description("Room1")
                                .isPrivate(false)
                                .build();
                room2 = new Room.Builder()
                                .description("Room2")
                                .isPrivate(true)
                                .build();

                // Set participants
                RoomParticipant participation1 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(user1)
                                .role(ParticipantRole.MEMBER)
                                .build();
                RoomParticipant participation2 = new RoomParticipant.Builder()
                                .room(room1)
                                .user(user2)
                                .role(ParticipantRole.MEMBER)
                                .build();

                room1 = entityManager.persistAndFlush(room1);
                room2 = entityManager.persistAndFlush(room2);
                entityManager.persistAndFlush(participation1);
                entityManager.persistAndFlush(participation2);
        }

        @Test
        void testFindAllByRoomId_ShouldReturnMessagesInRoom() {
                // Given
                Message message1 = new Message.Builder()
                                .room(room1)
                                .user(user1)
                                .content("Hello from User1 in Room1")
                                .build();
                Message message2 = new Message.Builder()
                                .room(room1)
                                .user(user2)
                                .content("Hello from User2 in Room1")
                                .build();
                Message message3 = new Message.Builder()
                                .room(room2)
                                .user(user2)
                                .content("Hello from User2 in Room2")
                                .build();

                entityManager.persistAndFlush(message1);
                entityManager.persistAndFlush(message2);
                entityManager.persistAndFlush(message3);

                // When
                List<Message> messagesInRoom1 = messageRepository.findAllByRoomId(room1.getId());

                // Then
                Assertions.assertThat(messagesInRoom1)
                                .hasSize(2)
                                .extracting(Message::getContent)
                                .containsExactlyInAnyOrder(
                                                "Hello from User1 in Room1",
                                                "Hello from User2 in Room1");
        }

        @Test
        void findAllByRoomId_ShouldReturnEmptyListWhenNoMessages() {
                // When
                List<Message> messages = messageRepository.findAllByRoomId(room1.getId());
                // Then
                Assertions.assertThat(messages).isEmpty();
        }

        @Test
        void findAllByRoomId_ShouldReturnEmptyListForNonExistentRoom() {
                // Given
                Long nonExistentRoomId = 999L;
                // When
                List<Message> messages = messageRepository.findAllByRoomId(nonExistentRoomId);
                // Then
                Assertions.assertThat(messages).isEmpty();
        }

        @Test
        void findAllByUserId_ShouldReturnMessagesFromUser() {
                // Given
                Message message1 = new Message.Builder()
                                .room(room1)
                                .user(user1)
                                .content("Message from User1 in Room1")
                                .build();
                Message message2 = new Message.Builder()
                                .room(room1)
                                .user(user2)
                                .content("Message from User2 in Room1")
                                .build();
                Message message3 = new Message.Builder()
                                .room(room2)
                                .user(user2)
                                .content("Message from User2 in Room2")
                                .build();

                entityManager.persistAndFlush(message1);
                entityManager.persistAndFlush(message2);
                entityManager.persistAndFlush(message3);

                // When
                List<Message> messagesFromUser2 = messageRepository.findAllByUserId(user2.getId());

                // Then
                Assertions.assertThat(messagesFromUser2)
                                .hasSize(2)
                                .extracting(Message::getContent)
                                .containsExactlyInAnyOrder(
                                                "Message from User2 in Room1",
                                                "Message from User2 in Room2");
        }

        @Test
        void findAllByUserId_ShouldReturnEmptyListForNonExistentUser() {
                // Given
                Long nonExistentUserId = 9999999L;

                // When
                List<Message> messages = messageRepository.findAllByUserId(nonExistentUserId);

                // Then
                Assertions.assertThat(messages).isEmpty();
        }

        @Test
        void findAllByRoomIdAndUserId_ShouldReturnMessagesInRoomFromUser() {
                // Given
                Message message1 = new Message.Builder()
                                .room(room1)
                                .user(user1)
                                .content("Message from User1 in Room1")
                                .build();
                Message message2 = new Message.Builder()
                                .room(room1)
                                .user(user2)
                                .content("Message from User2 in Room1")
                                .build();
                Message message3 = new Message.Builder()
                                .room(room2)
                                .user(user2)
                                .content("Message from User2 in Room2")
                                .build();
                entityManager.persistAndFlush(message1);
                entityManager.persistAndFlush(message2);
                entityManager.persistAndFlush(message3);

                // When
                List<Message> messagesFromUser2InRoom1 = messageRepository.findAllByRoomIdAndUserId(room1.getId(),
                                user2.getId());

                // Then
                Assertions.assertThat(messagesFromUser2InRoom1)
                                .hasSize(1)
                                .extracting(Message::getContent)
                                .containsExactly("Message from User2 in Room1");
        }

        @Test
        void findAllByRoomIdAndUserId_ShouldReturnEmptyListWhenNoMessages() {
                // Given
                Long nonExistentRoomId = 9999999L;
                Long nonExistentUserId = 8888888L;
                // When
                List<Message> messages = messageRepository.findAllByRoomIdAndUserId(nonExistentRoomId,
                                nonExistentUserId);
                // Then
                Assertions.assertThat(messages).isEmpty();
        }

        @Test
        void findAllByRoomId_ShouldReturnMessagesInCorrectOrder() {
                // Given
                Message message1 = new Message.Builder()
                                .room(room1)
                                .user(user1)
                                .content("First message")
                                .build();
                Message message2 = new Message.Builder()
                                .room(room1)
                                .user(user2)
                                .content("Second message")
                                .build();
                Message message3 = new Message.Builder()
                                .room(room1)
                                .user(user1)
                                .content("Third message")
                                .build();

                entityManager.persistAndFlush(message1);
                entityManager.persistAndFlush(message2);
                entityManager.persistAndFlush(message3);

                // When
                List<Message> messagesInRoom1 = messageRepository.findAllByRoomId(room1.getId());

                // Then
                Assertions.assertThat(messagesInRoom1)
                                .hasSize(3)
                                .extracting(Message::getContent)
                                .containsExactly(
                                                "First message",
                                                "Second message",
                                                "Third message");
        }
}
