package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.room.*;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.User;

import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Component
/**
 * Mapper class for converting between Room entities and Room DTOs.
 */
public class RoomMapper {

    private RoomMapper() {
    }

    /**
     * Converts a Room entity to a RoomDto.
     * 
     * @param room the Room entity
     * @return the corresponding RoomDto
     */
    public static RoomDto toRoomDto(Room room) {
        Long id = room.getId();
        String description = room.getDescription();
        Instant created = room.getCreatedOn();
        Boolean isPrivate = room.getPrivate();
        Set<User> participants = room.getParticipantUsers();
        Set<Long> participantIds = participants.stream().map(User::getId).collect(Collectors.toSet());
        Set<String> participantNames = participants.stream().map(User::getUsername)
                .collect(Collectors.toSet());
        Set<String> allowedUsernames = room.getAllowedUserNicknames();
        return new RoomDto(id, description, created, isPrivate, participantIds, participantNames,
                allowedUsernames);
    }

    /**
     * Converts a RoomCreationDto to a Room entity.
     * 
     * @param roomDto the RoomCreationDto
     * @param owner   the User who will own the room
     * @return the corresponding Room entity
     */
    public static Room toRoom(RoomCreationDto roomDto) {
        return new Room.Builder()
                .description(roomDto.getDescription())
                .isPrivate(roomDto.getPrivate())
                .password(roomDto.getPassword())
                .build();
    }

    /**
     * Updates an existing Room entity with data from a RoomUpdateDto.
     * 
     * @param roomDto         the RoomUpdateDto containing updated data
     * @param roomToBeUpdated the existing Room entity to be updated
     * @param owner           the User who will own the room
     * @param participants    the set of RoomParticipants who will participate in
     *                        the room
     * @return the updated Room entity
     */
    public static Room updatedRoom(RoomUpdateDto roomDto, Room roomToBeUpdated) {
        if (roomDto.getDescription() != null) {
            roomToBeUpdated.setDescription(roomDto.getDescription());
        }
        if (roomDto.getPrivate() != null) {
            roomToBeUpdated.setIsPrivate(roomDto.getPrivate());
        }
        if (roomDto.getNewPassword() != null) {
            roomToBeUpdated.setPassword(roomDto.getNewPassword());
        }
        if (roomDto.getAllowedUsernames() != null && !roomDto.getAllowedUsernames().isEmpty()) {
            roomToBeUpdated.setAllowedUserNicknames(roomDto.getAllowedUsernames());
        }
        return roomToBeUpdated;
    }
}
