package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.room.*;
import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantCreationDto;
import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantDto;
import com.mychat.chat_backend.exception.*;
import com.mychat.chat_backend.mapper.*;
import com.mychat.chat_backend.model.*;
import com.mychat.chat_backend.model.enums.ParticipantRole;
import com.mychat.chat_backend.repository.*;
import com.mychat.chat_backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@Validated
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;
    private RoomParticipantRepository roomParticipantRepository;
    private UserRepository userRepository;

    @SuppressWarnings("unused")
    private RoomServiceImpl() {
    }

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository,
            RoomParticipantRepository roomParticipantRepository,
            UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.roomParticipantRepository = roomParticipantRepository;
        this.userRepository = userRepository;
    }

    // === Room CRUD ===

    @Override
    public RoomDto getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        return RoomMapper.toRoomDto(room);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::toRoomDto)
                .toList();
    }

    @Override
    public RoomDto createRoom(RoomCreationDto createDto) {
        Room newRoom = RoomMapper.toRoom(createDto);
        return RoomMapper.toRoomDto(roomRepository.save(newRoom));
    }

    @Override
    public RoomDto updateRoom(RoomUpdateDto updateDto, Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        Room updatedRoom = RoomMapper.updatedRoom(updateDto, room);
        updatedRoom.setUpdatedOn();
        return RoomMapper.toRoomDto(roomRepository.save(updatedRoom));
    }

    @Override
    public List<RoomDto> getRoomsByParticipantId(Long participantId) {
        return roomRepository.findAllByParticipantsUserId(participantId).stream()
                .map(RoomMapper::toRoomDto)
                .toList();
    }

    @Override
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        roomRepository.delete(room);
    }

    // === Participant Management ===

    @Override
    public RoomParticipantDto addParticipant(Long roomId, RoomParticipantCreationDto creationDto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
        User user = userRepository.findById(creationDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        RoomParticipant participant = new RoomParticipant.Builder()
                .room(room)
                .user(user)
                .role(creationDto.getRole())
                .build();

        return RoomParticipantMapper.toRoomParticipantDto(roomParticipantRepository.save(participant));
    }

    @Override
    public void removeParticipant(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        RoomParticipant participant = roomParticipantRepository.findByUserAndRoom(user, room)
                .orElseThrow(RoomParticipantNotFoundException::new);

        roomParticipantRepository.delete(participant);
    }

    @Override
    public RoomParticipantDto updateParticipantRole(Long roomId, Long userId, ParticipantRole newRole) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        RoomParticipant participant = roomParticipantRepository.findByUserAndRoom(user, room)
                .orElseThrow(RoomParticipantNotFoundException::new);

        participant.setRole(newRole);
        return RoomParticipantMapper.toRoomParticipantDto(roomParticipantRepository.save(participant));
    }

    @Override
    public List<RoomParticipantDto> getParticipants(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        return roomParticipantRepository.findAllByRoom(room).stream()
                .map(RoomParticipantMapper::toRoomParticipantDto)
                .toList();
    }

    @Override
    public List<RoomParticipantDto> getUserParticipations(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return roomParticipantRepository.findAllByUser(user).stream()
                .map(RoomParticipantMapper::toRoomParticipantDto)
                .toList();
    }
}
