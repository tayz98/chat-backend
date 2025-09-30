package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findAllByRecipientId(Long userId);
}
