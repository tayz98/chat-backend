package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.room.*;
import java.util.List;
import com.mychat.chat_backend.exception.*;

/**
 * Service interface for managing chat rooms.
 */
public interface RoomService {

    /**
     * Get a room by its id
     *
     * @param roomId Room id
     * @return Room DTO
     * @throws RoomNotFoundException if the room is not found
     */
    RoomDto getRoomById(Long roomId);

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
    RoomDto createRoom(RoomCreationDto roomDto);

    /**
     * Update an existing room
     *
     * @param roomDto Room update DTO
     * @param roomId  Room id
     * @return Updated Room DTO
     * @throws RoomNotFoundException if the room is not found
     */
    RoomDto updateRoom(RoomUpdateDto roomDto, Long roomId);

    /**
     * Get rooms by participant id
     *
     * @param participantId Participant user id
     * @return List of Room DTOs
     */
    List<RoomDto> getRoomsByParticipantId(Long participantId);

    /**
     * Delete a room
     *
     * @param roomId Room id
     */
    void deleteRoom(Long roomId);
}
