package com.mychat.chat_backend.mapper;

import java.time.Instant;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.mychat.chat_backend.dto.room.RoomSummaryDto;
import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantCreationDto;
import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantDto;
import com.mychat.chat_backend.dto.user.UserSummaryDto;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;

@Component
/**
 * Mapper class for converting between RoomParticipant entities and
 * RoomParticipant DTOs.
 */
public class RoomParticipantMapper {
    private RoomParticipantMapper() {
    }

    /**
     * Converts a RoomParticipant entity to a RoomParticipantDto.
     *
     * @param roomParticipant the RoomParticipant entity
     * @return the corresponding RoomParticipantDto
     */
    public static RoomParticipantDto toRoomParticipantDto(RoomParticipant roomParticipant) {
        // RoomParticipant data
        Long id = roomParticipant.getId();
        Long userId = roomParticipant.getUser().getId();
        ParticipantRole role = roomParticipant.getRole();
        Instant createdOn = roomParticipant.getCreatedOn();

        // User data
        String username = roomParticipant.getUser().getUsername();
        String avatarUrl = roomParticipant.getUser().getAvatarUrl();
        UserSummaryDto user = new UserSummaryDto(userId, username, avatarUrl);

        // Room data
        Long roomId = roomParticipant.getRoom().getId();
        String roomDescription = roomParticipant.getRoom().getDescription();
        Boolean roomIsPrivate = roomParticipant.getRoom().getPrivate();
        RoomSummaryDto room = new RoomSummaryDto(roomId, roomDescription, roomIsPrivate);

        return new RoomParticipantDto(id, room, role, user, createdOn);
    }

    /**
     * Converts a RoomParticipantCreationDto to a RoomParticipant entity.
     *
     * @param creationDto the RoomParticipantCreationDto
     * @param user        the User entity associated with the participant
     * @param room        the Room entity associated with the participant
     * @return the corresponding RoomParticipant entity
     * @throws IllegalArgumentException if the IDs in the DTO do not match the
     *                                  provided User and Room
     */
    public static RoomParticipant toRoomParticipant(RoomParticipantCreationDto creationDto, User user, Room room) {
        if (!Objects.equals(creationDto.getUserId(), user.getId()) ||
                !Objects.equals(creationDto.getRoomId(), room.getId())) {
            throw new IllegalArgumentException("Creation DTO IDs do not match provided User and Room");
        }
        return new RoomParticipant.Builder()
                .user(user)
                .room(room)
                .role(creationDto.getRole())
                .build();
    }
}
