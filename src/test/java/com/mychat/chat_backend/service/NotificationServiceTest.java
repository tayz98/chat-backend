package com.mychat.chat_backend.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mychat.chat_backend.dto.notification.NotificationCreationDto;
import com.mychat.chat_backend.dto.notification.NotificationDto;
import com.mychat.chat_backend.dto.notification.NotificationUpdateDto;
import com.mychat.chat_backend.mapper.NotificationMapper;
import com.mychat.chat_backend.model.Notification;
import com.mychat.chat_backend.model.enums.NotificationType;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.repository.NotificationRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.service.impl.NotificationServiceImpl;
import com.mychat.chat_backend.exception.NotificationNotFoundException;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

        @Mock
        private NotificationRepository notificationRepository;

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private NotificationServiceImpl notificationService;

        private User testUserWithNotifications;
        private Notification testNotification;
        private NotificationDto testNotificationDto;

        @BeforeEach
        void setUp() {
                testUserWithNotifications = new User.Builder()
                                .username("Testuser")
                                .password("1234")
                                .email("test@example.com")
                                .isAdmin(false)
                                .build();
                testNotification = new Notification.Builder()
                                .recipient(testUserWithNotifications)
                                .type(NotificationType.FRIEND_REQUEST)
                                .build();
                testNotificationDto = NotificationMapper.toNotificationDto(testNotification);
        }

        @Test
        void getNotificationById_WhenExists_ReturnsNotificationDto() {
                when(notificationRepository.findById(testNotification.getId()))
                                .thenReturn(Optional.of(testNotification));
                NotificationDto resultDto = notificationService.getNotificationById(testNotification.getId());
                Assertions.assertEquals(testNotificationDto, resultDto);
                verify(notificationRepository, times(1)).findById(testNotification.getId());
        }

        @Test
        void getNotificationById_WhenNotExists_ThrowsException() {
                Long nonExistentId = 999L;
                when(notificationRepository.findById(nonExistentId))
                                .thenReturn(Optional.empty());
                Assertions.assertThrows(NotificationNotFoundException.class,
                                () -> notificationService.getNotificationById(nonExistentId));
                verify(notificationRepository, times(1)).findById(nonExistentId);
        }

        @Test
        void getNotificationsByRecipientId_WhenExists_ReturnsListOfNotificationDtos() {
                Notification secondNotification = new Notification.Builder()
                                .recipient(testUserWithNotifications)
                                .type(NotificationType.MENTION)
                                .build();
                when(userRepository.findById(testUserWithNotifications.getId()))
                                .thenReturn(Optional.of(testUserWithNotifications));
                when(notificationRepository.findAllByRecipientId(testUserWithNotifications.getId()))
                                .thenReturn(List.of(testNotification, secondNotification));
                List<NotificationDto> resultDtos = notificationService
                                .getNotificationsByRecipientId(testUserWithNotifications.getId());
                Assertions.assertEquals(2, resultDtos.size());
                verify(userRepository, times(1)).findById(testUserWithNotifications.getId());
                verify(notificationRepository, times(1)).findAllByRecipientId(testUserWithNotifications.getId());
        }

        @Test
        void createNotification_SavesAndReturnsNotificationDto() {
                when(userRepository.findById(testUserWithNotifications.getId()))
                                .thenReturn(Optional.of(testUserWithNotifications));
                when(notificationRepository.save(ArgumentMatchers.any(Notification.class)))
                                .thenReturn(testNotification);
                NotificationCreationDto creationDto = new NotificationCreationDto(NotificationType.FRIEND_REQUEST,
                                testUserWithNotifications.getId());
                NotificationDto resultDto = notificationService.createNotification(creationDto);
                Assertions.assertEquals(testNotificationDto, resultDto);
                verify(userRepository, times(1)).findById(testUserWithNotifications.getId());
                verify(notificationRepository, times(1)).save(ArgumentMatchers.any(Notification.class));
        }

        @Test
        void updateNotification_WhenExists_UpdatesAndReturnsNotificationDto() {
                when(notificationRepository.findById(testNotification.getId()))
                                .thenReturn(Optional.of(testNotification));
                when(notificationRepository.save(ArgumentMatchers.any(Notification.class)))
                                .thenReturn(testNotification);
                NotificationUpdateDto updateDto = new NotificationUpdateDto(true);
                NotificationDto resultDto = notificationService
                                .updateNotification(updateDto, testNotification.getId());
                Assertions.assertEquals(true, resultDto.getRead());
                Assertions.assertNotEquals(testNotificationDto, resultDto);
                verify(userRepository, times(0)).findById(ArgumentMatchers.anyLong());
                verify(notificationRepository, times(1)).findById(testNotification.getId());
        }

        @Test
        void deleteNotification_WhenExists_DeletesNotification() {
                when(notificationRepository.findById(testNotification.getId()))
                                .thenReturn(Optional.of(testNotification));
                notificationService.deleteNotification(testNotification.getId());
                verify(notificationRepository, times(1)).findById(testNotification.getId());
                verify(notificationRepository, times(1)).delete(testNotification);
        }
}
