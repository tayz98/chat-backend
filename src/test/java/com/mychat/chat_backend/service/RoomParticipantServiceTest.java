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

import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantCreationDto;
import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantDto;
import com.mychat.chat_backend.mapper.RoomParticipantMapper;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;
import com.mychat.chat_backend.repository.RoomParticipantRepository;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.service.impl.RoomParticipantServiceImpl;

@ExtendWith(MockitoExtension.class)
class RoomParticipantServiceTest {

    @Mock
    private RoomParticipantRepository roomParticipantRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomParticipantServiceImpl roomParticipantService;

    private RoomParticipant testRoomParticipant;
    private Room testRoom;
    private User testUser;
    private RoomParticipantDto testRoomParticipantDto;

    @BeforeEach
    void setUp() {
        testUser = new User.Builder()
                .username("Testuser")
                .password("1234")
                .email("test@example.com")
                .isAdmin(false)
                .build();
        testRoom = new Room.Builder()
                .description("Room 1")
                .isPrivate(false)
                .password("")
                .build();
        testRoomParticipant = new RoomParticipant.Builder()
                .room(testRoom)
                .user(testUser)
                .role(ParticipantRole.OWNER)
                .build();
        testRoomParticipantDto = RoomParticipantMapper.toRoomParticipantDto(testRoomParticipant);
    }

    @Test
    void getRoomParticipantById_WhenExists_ReturnsRoomParticipantDto() {
        when(roomParticipantRepository.findById(1L)).thenReturn(Optional.of(testRoomParticipant));
        RoomParticipantDto result = roomParticipantService.getRoomParticipantById(1L);
        Assertions.assertEquals(testRoomParticipantDto.getUser(), result.getUser());
        Assertions.assertEquals(testRoomParticipantDto.getRoom(), result.getRoom());
        Assertions.assertEquals(testRoomParticipantDto.getRole(), result.getRole());
        verify(roomParticipantRepository, times(1)).findById(1L);
    }

    @Test
    void getAllRoomParticipants_ReturnsListOfRoomParticipantDtos() {
        User newUser = new User.Builder()
                .username("AnotherUser")
                .password("abcd")
                .email("adsfklj@gmail.com")
                .isAdmin(false)
                .build();
        Room newRoom = new Room.Builder()
                .description("Room 2")
                .isPrivate(true)
                .password("pass123")
                .build();
        RoomParticipant newRoomParticipant = new RoomParticipant.Builder()
                .room(newRoom)
                .user(newUser)
                .role(ParticipantRole.MEMBER)
                .build();
        when(roomParticipantRepository.findAll()).thenReturn(List.of(testRoomParticipant, newRoomParticipant));
        List<RoomParticipantDto> result = roomParticipantService.getAllRoomParticipants();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(testRoomParticipantDto.getUser(), result.get(0).getUser());
        Assertions.assertEquals(testRoomParticipantDto.getRoom(), result.get(0).getRoom());
        Assertions.assertEquals(testRoomParticipantDto.getRole(), result.get(0).getRole());
        verify(roomParticipantRepository, times(1)).findAll();
    }

    @Test
    void createRoomParticipant_SavesAndReturnsRoomParticipantDto() {
        when(roomParticipantRepository.save(ArgumentMatchers.any(RoomParticipant.class)))
                .thenReturn(testRoomParticipant);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
        RoomParticipantCreationDto creationDto = new RoomParticipantCreationDto();
        creationDto.setRole(ParticipantRole.MEMBER);
        creationDto.setRoomId(1L);
        creationDto.setUserId(1L);
        RoomParticipantDto result = roomParticipantService.createRoomParticipant(creationDto);
        Assertions.assertEquals(testRoomParticipantDto.getUser(), result.getUser());
        Assertions.assertEquals(testRoomParticipantDto.getRoom(), result.getRoom());
        Assertions.assertEquals(testRoomParticipantDto.getRole(), result.getRole());
        verify(userRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).findById(1L);
        verify(roomParticipantRepository, times(1)).save(ArgumentMatchers.any(RoomParticipant.class));
    }

    @Test
    void updateRoomParticipantRole_WhenExists_UpdatesAndReturnsRoomParticipantDto() {
        when(roomParticipantRepository.findById(1L)).thenReturn(Optional.of(testRoomParticipant));
        when(roomParticipantRepository.save(ArgumentMatchers.any(RoomParticipant.class)))
                .thenReturn(testRoomParticipant);
        RoomParticipantDto result = roomParticipantService.updateRoomParticipantRole(1L, ParticipantRole.MEMBER);
        Assertions.assertEquals(ParticipantRole.MEMBER, result.getRole());
        verify(roomParticipantRepository, times(1)).findById(1L);
        verify(roomParticipantRepository, times(1)).save(ArgumentMatchers.any(RoomParticipant.class));
    }

    @Test
    void deleteRoomParticipant_WhenExists_DeletesRoomParticipant() {
        when(roomParticipantRepository.findById(1L)).thenReturn(Optional.of(testRoomParticipant));
        roomParticipantService.deleteRoomParticipant(1L);
        Assertions.assertEquals(0, roomParticipantRepository.findAll().size());
        verify(roomParticipantRepository, times(1)).findById(1L);
        verify(roomParticipantRepository, times(1)).delete(testRoomParticipant);
    }

    @Test
    void getRoomParticipantsByRoomId_WhenRoomExists_ReturnsListOfRoomParticipantDtos() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
        when(roomParticipantRepository.findAllByRoom(testRoom))
                .thenReturn(List.of(testRoomParticipant));
        List<RoomParticipantDto> result = roomParticipantService.getRoomParticipantsByRoomId(1L);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(testRoomParticipantDto.getUser(), result.get(0).getUser());
        Assertions.assertEquals(testRoomParticipantDto.getRoom(), result.get(0).getRoom());
        Assertions.assertEquals(testRoomParticipantDto.getRole(), result.get(0).getRole());
        verify(roomRepository, times(1)).findById(1L);
        verify(roomParticipantRepository, times(1)).findAllByRoom(testRoom);
    }

    @Test
    void getRoomParticipantsByUserId_WhenUserExists_ReturnsListOfRoomParticipantDtos() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(roomParticipantRepository.findAllByUser(testUser))
                .thenReturn(List.of(testRoomParticipant));
        List<RoomParticipantDto> result = roomParticipantService.getRoomParticipantsByUserId(1L);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(testRoomParticipantDto.getUser(), result.get(0).getUser());
        Assertions.assertEquals(testRoomParticipantDto.getRoom(), result.get(0).getRoom());
        Assertions.assertEquals(testRoomParticipantDto.getRole(), result.get(0).getRole());
        verify(userRepository, times(1)).findById(1L);
        verify(roomParticipantRepository, times(1)).findAllByUser(testUser);
    }
}