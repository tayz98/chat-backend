package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.room.RoomCreationDto;
import com.mychat.chat_backend.dto.room.RoomDto;
import com.mychat.chat_backend.dto.room.RoomUpdateDto;
import com.mychat.chat_backend.exception.RoomNotFoundException;
import com.mychat.chat_backend.mapper.RoomMapper;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.service.RoomService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@Validated
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    @SuppressWarnings("unused")
    private RoomServiceImpl() {
    }

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomDto getRoomById(@NotNull Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow((RoomNotFoundException::new));
        return RoomMapper.toRoomDto(room);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(RoomMapper::toRoomDto).toList();
    }

    @Override
    public RoomDto createRoom(@NotNull RoomCreationDto createDto) {
        Room newRoom = RoomMapper.toRoom(createDto);
        return RoomMapper.toRoomDto(roomRepository.save(newRoom));
    }

    @Override
    public RoomDto updateRoom(@NotNull RoomUpdateDto updateDto, @NotNull Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow((RoomNotFoundException::new));
        Room updatedRoom = RoomMapper.updatedRoom(updateDto, room);
        updatedRoom.setUpdatedOn();
        return RoomMapper.toRoomDto(roomRepository.save(updatedRoom));
    }

    @Override
    public void deleteRoom(@NotNull Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow((RoomNotFoundException::new));
        roomRepository.delete(room);
    }

    @Override
    public List<RoomDto> getRoomsByParticipantId(Long participantId) {
        List<Room> rooms = roomRepository.findAllByParticipantsUserId(participantId);
        return rooms.stream().map(RoomMapper::toRoomDto).toList();
    }
}
