package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.room.*;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;

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
        if (!roomDto.getAllowedUsernames().isEmpty()) {
            roomToBeUpdated.setAllowedUserNicknames(roomDto.getAllowedUsernames());
        }
        return roomToBeUpdated;
    }

    /**
     * Converts a RoomParticipantDto to a RoomParticipant entity.
     * 
     * @param participationDto the RoomParticipantDto
     * @param user             the User participating in the room
     * @param room             the Room in which the user is participating
     * @return the corresponding RoomParticipant entity
     */
    public static RoomParticipant toRoomParticipant(RoomParticipantDto participationDto, User user, Room room) {
        return new RoomParticipant.Builder()
                .user(user)
                .room(room)
                .role(participationDto.getRole())
                .build();
    }

    /**
     * Converts a RoomParticipant entity to a RoomParticipantDto.
     * 
     * @param participation the RoomParticipant entity
     * @return the corresponding RoomParticipantDto
     */
    public static RoomParticipantDto toRoomParticipantDto(RoomParticipant participation) {
        Long id = participation.getId();
        Long roomId = participation.getRoom().getId();
        Long userId = participation.getUser().getId();
        ParticipantRole role = participation.getRole();
        Instant createdOn = participation.getCreatedOn();
        return new RoomParticipantDto(id, roomId, userId, role, createdOn);
    }

}
