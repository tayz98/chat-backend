package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.notification.*;

import jakarta.validation.constraints.NotNull;

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
    NotificationDto getNotificationById(@NotNull Long notificationId);

    /**
     * Get all notifications for a specific recipient
     *
     * @param recipientId Recipient user id
     * @return List of Notification DTOs
     */
    List<NotificationDto> getNotificationsByRecipientId(@NotNull Long recipientId);

    /**
     * Create a new notification
     *
     * @param notificationDto Notification creation DTO
     * @return Created Notification DTO
     */
    NotificationDto createNotification(@NotNull NotificationCreationDto notificationDto);

    /**
     * Update an existing notification
     *
     * @param notificationDto Notification update DTO
     * @param notificationId  Notification id
     * @return Updated Notification DTO
     */
    NotificationDto updateNotification(@NotNull NotificationUpdateDto notificationDto, @NotNull Long notificationId);

    /**
     * Delete a notification
     *
     * @param notificationId Notification id
     */
    void deleteNotification(@NotNull Long notificationId);
}
