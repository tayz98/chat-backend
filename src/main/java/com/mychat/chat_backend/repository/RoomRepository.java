package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.Room;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Room repository
 * Contains methods to access rooms in the database
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Finds all rooms that a participant is part of.
     *
     * @param participantId the participant's user ID
     * @return a list of Room entities
     */
    List<Room> findAllByParticipantsUserId(Long participantId);

    /**
     * Finds the room that contains a specific message.
     *
     * @param messageId the message ID
     * @return an Optional containing the Room if found, or empty if not found
     */
    Optional<Room> findByMessagesId(Long messageId);

    /**
     * Finds all rooms based on their privacy status.
     *
     * @param isPrivate true for private rooms, false for public rooms
     * @return a list of Room entities
     */
    List<Room> findAllByIsPrivate(boolean isPrivate);

    /**
     * Finds all rooms with descriptions containing the specified keyword.
     *
     * @param keyword the keyword to search for in room descriptions
     * @return a list of Room entities
     */
    List<Room> findAllByDescriptionContaining(String keyword);

}
