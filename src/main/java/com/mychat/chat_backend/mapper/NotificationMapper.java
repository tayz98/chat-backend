package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.notification.NotificationCreationDto;
import com.mychat.chat_backend.dto.notification.NotificationDto;
import com.mychat.chat_backend.model.Notification;
import com.mychat.chat_backend.model.NotificationType;
import com.mychat.chat_backend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class NotificationMapper {

    public static NotificationDto toNotificationDto(Notification notification) {
        long id = notification.getId();
        String content = notification.getContent();
        Instant timestamp = notification.getTimestamp();
        Boolean isRead = notification.getRead();
        long recipientId = notification.getRecipient().getId();
        NotificationType type = notification.getType();
        return new NotificationDto(id, content, timestamp, isRead, recipientId, type);
    }

    public static Notification toNotification(NotificationCreationDto notificationDto, UserRepository userRepository) {
        return new Notification(notificationDto.getContent(), userRepository.findById(notificationDto.getRecipientId()).orElseThrow(), notificationDto.getType());
    }

    public static Notification updatedNotification(NotificationDto notificationDto, Notification notification) {
        if (notificationDto.getContent() != null) {
            notification.setContent(notificationDto.getContent());
        }
        if (notificationDto.getRead() != null) {
            notification.setRead(notificationDto.getRead());
        }
        if (notificationDto.getType() != null) {
            notification.setType(notificationDto.getType());
        }
        return notification;
    }
}
