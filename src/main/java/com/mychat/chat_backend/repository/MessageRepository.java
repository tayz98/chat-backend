package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Message repository
 * Contains methods to access messages in the database
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Find all messages in a room
     *
     * @param roomId
     * @return List of messages in the given room id
     */
    public List<Message> findAllByRoomId(Long roomId);

    /**
     * Find all messages sent by a user
     *
     * @param userId
     * @return List of messages sent by the given user id
     */
    public List<Message> findAllByUserId(Long userId);

    /**
     * Find all messages in a room sent by a user
     *
     * @param roomId
     * @param userId
     * @return List of messages in the given room id sent by the given user id
     */
    public List<Message> findAllByRoomIdAndUserId(Long roomId, Long userId);
}
