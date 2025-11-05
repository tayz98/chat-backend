package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantDto;
import java.util.List;

/**
 * Service interface for managing room participants.
 */
public interface RoomParticipantService {

    /**
     * Get a room participant by its id
     *
     * @param roomParticipantId RoomParticipant id
     * @return RoomParticipant DTO
     */
    RoomParticipantDto getRoomParticipantById(Long roomParticipantId);

    /**
     * Add a user as a participant to a room
     *
     * @param roomId Room id
     * @param userId User id
     * @param role   Participant role
     * @return Created RoomParticipant DTO
     */
    RoomParticipantDto addRoomParticipant(Long roomId, Long userId, String role);

    /**
     * Remove a room participant by its id
     *
     * @param roomParticipantId RoomParticipant id
     */
    void removeRoomParticipant(Long roomParticipantId);

    /**
     * Update the role of a room participant
     *
     * @param roomParticipantId RoomParticipant id
     * @param newRole           New participant role
     * @return Updated RoomParticipant DTO
     */
    RoomParticipantDto updateRoomParticipantRole(Long roomParticipantId, String newRole);

    /**
     * Get all room participants
     *
     * @return List of RoomParticipant DTOs
     */
    List<RoomParticipantDto> getAllRoomParticipants();

    /**
     * Get room participants by room id
     *
     * @param roomId Room id
     * @return List of RoomParticipant DTOs
     */
    List<RoomParticipantDto> getRoomParticipantsByRoomId(Long roomId);

    /**
     * Get room participants by user id
     *
     * @param userId User id
     * @return List of RoomParticipant DTOs
     */
    List<RoomParticipantDto> getRoomParticipantsByUserId(Long userId);

}
