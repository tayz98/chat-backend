package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.message.*;
import java.util.List;

/**
 * Service interface for managing messages.
 */
public interface MessageService {
    /**
     * Get a message by its id
     *
     * @param messageId Message id
     * @return Message DTO
     */
    MessageDto getMessageById(Long messageId);

    /**
     * Get all messages
     *
     * @return List of Message DTOs
     */
    List<MessageDto> getAllMessages();

    /**
     * Get all messages in a room
     *
     * @param roomId Room id
     * @return List of Message DTOs
     */
    List<MessageDto> getMessagesByRoomId(Long roomId);

    /**
     * Get all messages sent by a user
     *
     * @param userId User id
     * @return List of Message DTOs
     */
    List<MessageDto> getMessagesByUserId(Long userId);

    /**
     * Get all messages in a room sent by a specific user
     *
     * @param roomId Room id
     * @param userId User id
     * @return List of Message DTOs
     */
    List<MessageDto> getMessagesByRoomIdAndUserId(Long roomId, Long userId);

    /**
     * Create a new message
     *
     * @param messageDto Message creation DTO
     * @return Created Message DTO
     */
    MessageDto createMessage(MessageCreationDto messageDto);

    /**
     * Update an existing message
     *
     * @param messageDto Message update DTO
     * @param messageId  Message id
     * @return Updated Message DTO
     */
    MessageDto updateMessage(MessageUpdateDto messageDto, Long messageId);

    /**
     * Delete a message
     *
     * @param messageId Message id
     */
    void deleteMessage(Long messageId);

    // TODO: implement more filtering for a search function in the frontend
}
