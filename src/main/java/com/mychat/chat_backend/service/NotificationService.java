package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.notification.*;
import java.util.List;

/**
 * Service interface for managing notifications.
 */
public interface NotificationService {

    /**
     * Get a notification by its id
     *
     * @param notificationId Notification id
     * @return Notification DTO
     */
    NotificationDto getNotificationById(Long notificationId);

    /**
     * Get all notifications for a specific recipient
     *
     * @param recipientId Recipient user id
     * @return List of Notification DTOs
     */
    List<NotificationDto> getNotificationsByRecipientId(Long recipientId);

    /**
     * Create a new notification
     *
     * @param notificationDto Notification creation DTO
     * @return Created Notification DTO
     */
    NotificationDto createNotification(NotificationCreationDto notificationDto);

    /**
     * Update an existing notification
     *
     * @param notificationDto Notification update DTO
     * @param notificationId  Notification id
     * @return Updated Notification DTO
     */
    NotificationDto updateNotification(NotificationUpdateDto notificationDto, Long notificationId);

    /**
     * Delete a notification
     *
     * @param notificationId Notification id
     */
    void deleteNotification(Long notificationId);
}
