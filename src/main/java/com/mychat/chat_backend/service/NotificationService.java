package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.notification.NotificationCreationDto;
import com.mychat.chat_backend.dto.notification.NotificationDto;
import com.mychat.chat_backend.dto.notification.NotificationUpdateDto;

import java.util.List;

public interface NotificationService {

    NotificationDto getNotificationById(long notificationId);

    List<NotificationDto> getNotificationsByRecipientId(long recipientId);

    NotificationDto createNotification(NotificationCreationDto notificationDto);

    NotificationDto updateNotification(NotificationUpdateDto notificationDto);

    void deleteNotification(long notificationId);
}
