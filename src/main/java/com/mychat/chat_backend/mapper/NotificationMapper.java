package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.notification.*;
import com.mychat.chat_backend.model.Notification;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.NotificationType;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
/**
 * Mapper class for converting between Notification entities and DTOs.
 */
public class NotificationMapper {

    private NotificationMapper() {
    }

    /**
     * Converts a Notification entity to a NotificationDto.
     *
     * @param notification the Notification entity
     * @return the corresponding NotificationDto
     */
    public static NotificationDto toNotificationDto(Notification notification) {
        Long id = notification.getId();
        Instant timestamp = notification.getCreatedOn();
        Boolean isRead = notification.getRead();
        Long recipientId = notification.getRecipient().getId();
        NotificationType type = notification.getType();
        return new NotificationDto(id, timestamp, isRead, recipientId, type);
    }

    /**
     * Converts a NotificationCreationDto to a Notification entity.
     *
     * @param notificationDto the NotificationCreationDto
     * @param recipient       the User who will receive the notification
     * @return the corresponding Notification entity
     */
    public static Notification toNotification(NotificationCreationDto notificationDto, User recipient) {
        return new Notification.Builder()
                .recipient(recipient)
                .type(notificationDto.getType())
                .build();
    }

    /**
     * Updates an existing Notification entity with data from a
     * NotificationUpdateDto.
     *
     * @param notificationDto the NotificationUpdateDto containing updated data
     * @param notification    the existing Notification entity to be updated
     * @return the updated Notification entity
     */
    public static Notification updatedNotification(NotificationUpdateDto notificationDto, Notification notification) {

        if (notificationDto.isRead() != null) {
            notification.setRead(notificationDto.isRead());
        }
        return notification;
    }
}
