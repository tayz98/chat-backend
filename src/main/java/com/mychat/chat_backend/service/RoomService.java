package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.room.RoomCreationDto;
import com.mychat.chat_backend.dto.room.RoomDto;
import com.mychat.chat_backend.dto.room.RoomUpdateDto;

import java.util.List;

public interface RoomService {

    RoomDto getRoomById(long roomId);

    List<RoomDto> getAllRooms();

    List<RoomDto> getRoomsOfUser(long userId);
    
    List<RoomDto> getRoomsOfOwner(long ownerId);

    RoomDto createRoom(RoomCreationDto roomDto);

    RoomDto updateRoom(RoomUpdateDto roomDto, long roomId);

    void deleteRoom(long roomId);
}
