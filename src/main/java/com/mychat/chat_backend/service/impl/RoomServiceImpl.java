package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.room.RoomCreationDto;
import com.mychat.chat_backend.dto.room.RoomDto;
import com.mychat.chat_backend.dto.room.RoomUpdateDto;
import com.mychat.chat_backend.exception.RoomNotFoundException;
import com.mychat.chat_backend.exception.UserNotFoundException;
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
        Room room = roomRepository.findById(roomId).orElseThrow((RoomNotFoundException::new));
        return RoomMapper.toRoomDto(room);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(RoomMapper::toRoomDto).toList();
    }

    @Override
    public List<RoomDto> getRoomsOfUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow((UserNotFoundException::new));
        List<Room> rooms = roomRepository.findAllByParticipantsId(user.getId());
        return rooms.stream().map(RoomMapper::toRoomDto).toList();
    }

    @Override
    public List<RoomDto> getRoomsOfOwner(long ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow((UserNotFoundException::new));
        List<Room> rooms = roomRepository.findAllByOwner(owner);
        return rooms.stream().map(RoomMapper::toRoomDto).toList();
    }

    @Override
    public RoomDto createRoom(RoomCreationDto roomDto) {
        User owner = userRepository.findById(roomDto.getOwnerId()).orElseThrow(UserNotFoundException::new);
        Room newRoom = RoomMapper.toRoom(roomDto, owner);
        return RoomMapper.toRoomDto(roomRepository.save(newRoom));
    }

    @Override
    public RoomDto updateRoom(RoomUpdateDto roomDto, long roomId) {
        User owner = userRepository.findById(roomDto.getOwnerId()).orElseThrow((UserNotFoundException::new));
        Room room = roomRepository.findById(roomId).orElseThrow((RoomNotFoundException::new));
        List<Long> participantIds = roomDto.getParticipants();
        List<User> participants = participantIds.stream().map(userRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();
        Room updatedRoom = RoomMapper.updatedRoom(roomDto, room, owner, participants);
        return RoomMapper.toRoomDto(roomRepository.save(updatedRoom));
    }

    @Override
    public void deleteRoom(long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow((RoomNotFoundException::new));
        roomRepository.delete(room);
    }
}
