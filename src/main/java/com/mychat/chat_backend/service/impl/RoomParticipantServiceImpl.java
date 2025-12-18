package com.mychat.chat_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantCreationDto;
import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantDto;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;
import com.mychat.chat_backend.repository.RoomParticipantRepository;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.exception.RoomNotFoundException;
import com.mychat.chat_backend.exception.RoomParticipantNotFoundException;
import com.mychat.chat_backend.exception.UserNotFoundException;
import com.mychat.chat_backend.mapper.RoomParticipantMapper;
import com.mychat.chat_backend.service.RoomParticipantService;

@Service
@Validated
public class RoomParticipantServiceImpl implements RoomParticipantService {

    RoomParticipantRepository roomParticipantRepository;

    UserRepository userRepository;

    RoomRepository roomRepository;

    @SuppressWarnings("unused")
    private RoomParticipantServiceImpl() {
    }

    @Autowired
    public RoomParticipantServiceImpl(RoomParticipantRepository roomParticipantRepository,
            UserRepository userRepository,
            RoomRepository roomRepository) {
        this.roomParticipantRepository = roomParticipantRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomParticipantDto getRoomParticipantById(Long roomParticipantId) {
        RoomParticipant roomParticipant = roomParticipantRepository.findById(roomParticipantId)
                .orElseThrow(RoomParticipantNotFoundException::new);
        return RoomParticipantMapper.toRoomParticipantDto(roomParticipant);
    }

    @Override
    public RoomParticipantDto createRoomParticipant(RoomParticipantCreationDto creationDto) {
        User participant = userRepository.findById(creationDto.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Room room = roomRepository.findById(creationDto.getRoomId())
                .orElseThrow(RoomNotFoundException::new);
        RoomParticipant roomParticipant = new RoomParticipant.Builder()
                .user(participant)
                .room(room)
                .role(creationDto.getRole())
                .build();
        roomParticipant = roomParticipantRepository.save(roomParticipant);
        return RoomParticipantMapper.toRoomParticipantDto(roomParticipant);
    }

    @Override
    public void deleteRoomParticipant(Long roomParticipantId) {
        RoomParticipant roomParticipant = roomParticipantRepository.findById(roomParticipantId)
                .orElseThrow(RoomParticipantNotFoundException::new);
        roomParticipantRepository.delete(roomParticipant);

    }

    @Override
    public RoomParticipantDto updateRoomParticipantRole(Long roomParticipantId, ParticipantRole newRole) {
        RoomParticipant roomParticipant = roomParticipantRepository.findById(roomParticipantId)
                .orElseThrow(RoomParticipantNotFoundException::new);
        roomParticipant.setRole(newRole);
        roomParticipant = roomParticipantRepository.save(roomParticipant);
        return RoomParticipantMapper.toRoomParticipantDto(roomParticipant);
    }

    @Override
    public List<RoomParticipantDto> getAllRoomParticipants() {
        List<RoomParticipant> participants = roomParticipantRepository.findAll();
        return participants.stream().map(RoomParticipantMapper::toRoomParticipantDto).toList();
    }

    @Override
    public List<RoomParticipantDto> getRoomParticipantsByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
        List<RoomParticipant> participants = roomParticipantRepository.findAllByRoom(room);
        return participants.stream().map(RoomParticipantMapper::toRoomParticipantDto).toList();
    }

    @Override
    public List<RoomParticipantDto> getRoomParticipantsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        List<RoomParticipant> participants = roomParticipantRepository.findAllByUser(user);
        return participants.stream().map(RoomParticipantMapper::toRoomParticipantDto).toList();
    }
}
