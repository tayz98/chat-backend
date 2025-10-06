package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.room.RoomCreationDto;
import com.mychat.chat_backend.dto.room.RoomDto;
import com.mychat.chat_backend.dto.room.RoomUpdateDto;
import com.mychat.chat_backend.mapper.MessageMapper;
import com.mychat.chat_backend.mapper.RoomMapper;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {


    private RoomRepository roomRepository;
    private UserRepository userRepository;

    public RoomServiceImpl() {
    }

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RoomDto getRoomById(long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return RoomMapper.toRoomDto(room);
    }

    @Override
    public RoomDto createRoom(RoomCreationDto roomDto) {
        User owner = userRepository.findById(roomDto.getOwnerId()).orElseThrow();
        Room newRoom = RoomMapper.toRoom(roomDto, owner);
        return RoomMapper.toRoomDto(roomRepository.save(newRoom));
    }

    @Override
    public RoomDto updateRoom(RoomUpdateDto roomDto) {
        User owner = userRepository.findById(roomDto.getOwnerId()).orElseThrow();
        Room room = roomRepository.findById(roomDto.getRoomId()).orElseThrow();
        List<Long> participantIds = roomDto.getParticipants();
        List<User> participants = participantIds.stream().map(userRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();
        Room updatedRoom = RoomMapper.updatedRoom(roomDto, room, owner, participants);
        return RoomMapper.toRoomDto(roomRepository.save(updatedRoom));
    }

    @Override
    public void deleteRoom(long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        roomRepository.delete(room);
    }
}
