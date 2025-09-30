package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Notification repository
 * Contains methods to access notifications in the database
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Find all notifications sent to a user
     *
     * @param userId
     * @return List of notifications sent to the given user id
     */
    public List<Notification> findAllByRecipientId(Long userId);
}
