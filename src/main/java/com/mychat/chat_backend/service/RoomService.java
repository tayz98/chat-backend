package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.room.*;
import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantDto;
import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantCreationDto;
import com.mychat.chat_backend.model.enums.ParticipantRole;
import java.util.List;
import jakarta.validation.constraints.NotNull;

/**
 * Service interface for managing chat rooms and their participants.
 */
public interface RoomService {

    /**
     * Get a room by its id
     *
     * @param roomId Room id
     * @return Room DTO
     * @throws RoomNotFoundException if the room is not found
     */
    RoomDto getRoomById(@NotNull Long roomId);

    /**
     * Get all rooms
     *
     * @return List of Room DTOs
     */
    List<RoomDto> getAllRooms();

    /**
     * Create a new room
     *
     * @param roomDto Room creation DTO
     * @return Created Room DTO
     */
    RoomDto createRoom(@NotNull RoomCreationDto roomDto);

    /**
     * Update an existing room
     *
     * @param roomDto Room update DTO
     * @param roomId  Room id
     * @return Updated Room DTO
     * @throws RoomNotFoundException if the room is not found
     */
    RoomDto updateRoom(@NotNull RoomUpdateDto roomDto, @NotNull Long roomId);

    /**
     * Get rooms by participant id
     *
     * @param participantId Participant user id
     * @return List of Room DTOs
     */
    List<RoomDto> getRoomsByParticipantId(@NotNull Long participantId);

    /**
     * Delete a room
     *
     * @param roomId Room id
     */
    void deleteRoom(@NotNull Long roomId);

    // === Participant Management ===

    /**
     * Add a participant to a room
     *
     * @param roomId      Room id
     * @param creationDto Participant creation data
     * @return Created RoomParticipant DTO
     */
    RoomParticipantDto addParticipant(@NotNull Long roomId, @NotNull RoomParticipantCreationDto creationDto);

    /**
     * Remove a participant from a room
     *
     * @param roomId Room id
     * @param userId User id to remove
     */
    void removeParticipant(@NotNull Long roomId, @NotNull Long userId);

    /**
     * Update a participant's role in a room
     *
     * @param roomId  Room id
     * @param userId  User id
     * @param newRole New participant role
     * @return Updated RoomParticipant DTO
     */
    RoomParticipantDto updateParticipantRole(@NotNull Long roomId, @NotNull Long userId,
            @NotNull ParticipantRole newRole);

    /**
     * Get all participants in a room
     *
     * @param roomId Room id
     * @return List of RoomParticipant DTOs
     */
    List<RoomParticipantDto> getParticipants(@NotNull Long roomId);

    /**
     * Get all rooms a user is participating in
     *
     * @param userId User id
     * @return List of RoomParticipant DTOs
     */
    List<RoomParticipantDto> getUserParticipations(@NotNull Long userId);
}
