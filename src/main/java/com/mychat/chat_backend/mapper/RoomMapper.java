package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.room.RoomCreationDto;
import com.mychat.chat_backend.dto.room.RoomDto;
import com.mychat.chat_backend.dto.room.RoomUpdateDto;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class RoomMapper {
    public static RoomDto toRoomDto(Room room) {
        long id = room.getId();
        String description = room.getDescription();
        Instant created = room.getCreated();
        boolean isPrivate = room.getPrivate();
        long ownerId = room.getOwner().getId();
        String ownerName = room.getOwner().getUsername();
        List<Long> participantIds = room.getParticipants().stream().map(User::getId).toList();
        List<String> participantNames = room.getParticipants().stream().map(User::getUsername).toList();
        List<String> allowedUsernames = room.getAllowedUserNicknames();
        return new RoomDto(id, description, created, isPrivate, ownerId, ownerName, participantIds, participantNames, allowedUsernames);
    }

    public static Room toRoom(RoomCreationDto roomDto, User owner) {
        return new Room(roomDto.getDescription(), roomDto.getPrivate(), roomDto.getPassword(), owner);
    }

    public static Room updatedRoom(RoomUpdateDto roomDto, Room room, User owner, List<User> users) {
        if (roomDto.getDescription() != null) {
            room.setDescription(roomDto.getDescription());
        }
        if (roomDto.getPrivate() != null) {
            room.setPrivate(roomDto.getPrivate());
        }
        if (roomDto.getPassword() != null) {
            room.setPassword(roomDto.getPassword());
        }

        if (roomDto.getOwnerId() != null) {
            room.setOwner(owner);
        }

        if (!roomDto.getParticipants().isEmpty()) {
            room.setParticipants(users);
        }
        if (!roomDto.getAllowedUsernames().isEmpty()) {
            room.setAllowedUserNicknames(roomDto.getAllowedUsernames());
        }
        return room;
    }


}
