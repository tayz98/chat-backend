package com.mychat.chat_backend.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mychat.chat_backend.dto.room.RoomCreationDto;
import com.mychat.chat_backend.dto.room.RoomDto;
import com.mychat.chat_backend.dto.room.RoomUpdateDto;
import com.mychat.chat_backend.mapper.RoomMapper;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.service.impl.RoomServiceImpl;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    private Room testRoom;
    private RoomDto testRoomDto;

    @BeforeEach
    void setUp() {
        testRoom = new Room.Builder()
                .description("Room 1")
                .isPrivate(false)
                .password("")
                .build();
        testRoomDto = RoomMapper.toRoomDto(testRoom);
    }

    @Test
    void getRoomById_WhenRoomExists_ReturnsRoomDto() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
        RoomDto result = roomService.getRoomById(1L);
        Assertions.assertEquals(testRoomDto.getDescription(), result.getDescription());
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void getAllRooms_ReturnsListOfRoomDtos() {
        Room room2 = new Room.Builder()
                .description("Room 2")
                .isPrivate(true)
                .password("secret")
                .build();
        when(roomRepository.findAll()).thenReturn(List.of(testRoom, room2));
        List<RoomDto> result = roomService.getAllRooms();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(testRoomDto.getDescription(), result.get(0).getDescription());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void createRoom_SavesAndReturnsRoomDto() {
        when(roomRepository.save(ArgumentMatchers.any(Room.class))).thenReturn(testRoom);
        RoomCreationDto creationDto = new RoomCreationDto();
        creationDto.setDescription("Room 1");
        creationDto.setPrivate(false);
        creationDto.setPassword("");
        RoomDto result = roomService.createRoom(creationDto);
        Assertions.assertEquals(testRoomDto.getDescription(), result.getDescription());
        verify(roomRepository, times(1)).save(ArgumentMatchers.any(Room.class));
    }

    @Test
    void updateRoom_WhenRoomExists_UpdatesAndReturnsRoomDto() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
        when(roomRepository.save(ArgumentMatchers.any(Room.class))).thenReturn(testRoom);
        RoomUpdateDto updateDto = new RoomUpdateDto();
        updateDto.setDescription("Updated Room");
        updateDto.setPrivate(true);
        updateDto.setNewPassword("newpassword");
        RoomDto result = roomService.updateRoom(updateDto, 1L);
        Assertions.assertEquals("Updated Room", result.getDescription());
        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).save(ArgumentMatchers.any(Room.class));
    }

    @Test
    void deleteRoom_WhenRoomExists_DeletesRoom() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
        roomService.deleteRoom(1L);
        Assertions.assertEquals(0, roomRepository.findAll().size());
        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).delete(testRoom);
    }

    @Test
    void getRoomsByParticipantId_ReturnsListOfRoomDtos() {
        Long participantId = 10L;
        Room room2 = new Room.Builder()
                .description("Room 2")
                .isPrivate(true)
                .password("secret")
                .build();
        when(roomRepository.findAllByParticipantsUserId(participantId)).thenReturn(List.of(testRoom, room2));
        List<RoomDto> result = roomService.getRoomsByParticipantId(participantId);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(testRoomDto.getDescription(), result.get(0).getDescription());
        verify(roomRepository, times(1)).findAllByParticipantsUserId(participantId);
    }
}
