package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.notification.NotificationCreationDto;
import com.mychat.chat_backend.dto.notification.NotificationDto;
import com.mychat.chat_backend.dto.notification.NotificationUpdateDto;
import com.mychat.chat_backend.exception.NotificationNotFoundException;
import com.mychat.chat_backend.exception.UserNotFoundException;
import com.mychat.chat_backend.mapper.NotificationMapper;
import com.mychat.chat_backend.model.Notification;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.repository.NotificationRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    @SuppressWarnings("unused")
    private NotificationServiceImpl() {
    }

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationDto getNotificationById(Long notificationId) {
        return NotificationMapper.toNotificationDto(
                notificationRepository.findById(notificationId).orElseThrow(NotificationNotFoundException::new));
    }

    @Override
    public List<NotificationDto> getNotificationsByRecipientId(Long recipientId) {
        User user = userRepository.findById(recipientId).orElseThrow(UserNotFoundException::new);
        List<Notification> notifications = notificationRepository.findAllByRecipientId(user.getId());
        return notifications.stream().map(NotificationMapper::toNotificationDto).toList();
    }

    @Override
    public NotificationDto createNotification(NotificationCreationDto notificationDto) {
        User recipient = userRepository.findById(notificationDto.getRecipientId())
                .orElseThrow(UserNotFoundException::new);
        Notification newNotification = NotificationMapper.toNotification(notificationDto, recipient);
        return NotificationMapper.toNotificationDto(notificationRepository.save(newNotification));
    }

    @Override
    public NotificationDto updateNotification(NotificationUpdateDto notificationDto, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(NotificationNotFoundException::new);
        Notification updatedNotification = NotificationMapper.updatedNotification(notificationDto, notification);
        updatedNotification.setUpdatedOn();
        return NotificationMapper.toNotificationDto(notificationRepository.save(updatedNotification));
    }

    @Override
    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow((NotificationNotFoundException::new));
        notificationRepository.delete(notification);
    }
}
