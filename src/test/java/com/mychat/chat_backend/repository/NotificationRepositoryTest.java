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

import com.mychat.chat_backend.model.Notification;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.NotificationType;

/**
 * Tests for NotificationRepository.
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class NotificationRepositoryTest {

        @Autowired
        private NotificationRepository notificationRepository;

        @Autowired
        private TestEntityManager entityManager;

        private Notification notification1;
        private Notification notification2;
        private Notification notification3;
        private User recipient1;
        private User recipient2;

        @BeforeEach
        void setup() {
                // Create and persist recipients
                recipient1 = new User.Builder()
                                .username("Testuser")
                                .password("password")
                                .email("user@example.com")
                                .build();

                recipient2 = new User.Builder()
                                .username("AnotherUser")
                                .password("password")
                                .email("anotheruser@example.com")
                                .build();

                recipient1 = entityManager.persistAndFlush(recipient1);
                recipient2 = entityManager.persistAndFlush(recipient2);
        }

        @Test
        /**
         * * Test for finding notifications by recipient ID.
         */
        void findAllByRecipientId_ShouldReturnNotificationsForGivenUserId() {
                // Given
                // Create and persist notifications
                notification1 = new Notification.Builder()
                                .recipient(recipient1)
                                .type(NotificationType.FRIEND_REMOVED)
                                .build();
                notification2 = new Notification.Builder()
                                .recipient(recipient1)
                                .type(NotificationType.MENTION)
                                .build();
                notification3 = new Notification.Builder()
                                .recipient(recipient2)
                                .type(NotificationType.MENTION)
                                .build();

                notification1 = entityManager.persistAndFlush(notification1);
                notification2 = entityManager.persistAndFlush(notification2);
                notification3 = entityManager.persistAndFlush(notification3);

                // When
                List<Notification> notifications = notificationRepository.findAllByRecipientId(recipient1.getId());

                // Then
                Assertions.assertThat(notifications).hasSize(2).extracting(Notification::getType)
                                .containsExactlyInAnyOrder(NotificationType.MENTION, NotificationType.FRIEND_REMOVED);
        }

}
