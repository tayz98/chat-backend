package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.room.RoomCreationDto;
import com.mychat.chat_backend.dto.room.RoomDto;
import com.mychat.chat_backend.dto.room.RoomUpdateDto;

public interface RoomService {

    RoomDto getRoomById(long roomId);

    RoomDto createRoom(RoomCreationDto roomDto);

    RoomDto updateRoom(RoomUpdateDto roomDto);

    void deleteRoom(long roomId);
}
