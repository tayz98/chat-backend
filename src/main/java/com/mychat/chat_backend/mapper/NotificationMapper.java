package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.notification.NotificationCreationDto;
import com.mychat.chat_backend.dto.notification.NotificationDto;
import com.mychat.chat_backend.dto.notification.NotificationUpdateDto;
import com.mychat.chat_backend.model.Notification;
import com.mychat.chat_backend.model.NotificationType;
import com.mychat.chat_backend.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class NotificationMapper {

    private NotificationMapper() {
    }

    public static NotificationDto toNotificationDto(Notification notification) {
        Long id = notification.getId();
        String content = notification.getContent();
        Instant timestamp = notification.getCreatedOn();
        Boolean isRead = notification.getRead();
        Long recipientId = notification.getRecipient().getId();
        NotificationType type = notification.getType();
        return new NotificationDto(id, content, timestamp, isRead, recipientId, type);
    }

    public static Notification toNotification(NotificationCreationDto notificationDto, User recipient) {
        return new Notification.Builder()
                .content(notificationDto.getContent())
                .recipient(recipient)
                .type(notificationDto.getType())
                .build();
    }

    public static Notification updatedNotification(NotificationUpdateDto notificationDto, Notification notification) {
        if (notificationDto.getContent() != null) {
            notification.setContent(notificationDto.getContent());
        }
        if (notificationDto.isRead() != null) {
            notification.setRead(notificationDto.isRead());
        }
        if (notificationDto.getType() != null) {
            notification.setType(notificationDto.getType());
        }
        return notification;
    }
}
