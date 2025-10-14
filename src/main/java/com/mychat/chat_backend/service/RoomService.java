package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.room.RoomCreationDto;
import com.mychat.chat_backend.dto.room.RoomDto;
import com.mychat.chat_backend.dto.room.RoomUpdateDto;

import java.util.List;

public interface RoomService {

    RoomDto getRoomById(Long roomId);

    List<RoomDto> getAllRooms();

    List<RoomDto> getRoomsOfUser(Long userId);

    List<RoomDto> getRoomsOfOwner(Long ownerId);

    RoomDto createRoom(RoomCreationDto roomDto);

    RoomDto updateRoom(RoomUpdateDto roomDto, Long roomId);

    void deleteRoom(Long roomId);
}
