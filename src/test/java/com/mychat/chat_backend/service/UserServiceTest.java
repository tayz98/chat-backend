package com.mychat.chat_backend.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;
import com.mychat.chat_backend.dto.user.*;
import com.mychat.chat_backend.mapper.UserMapper;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.service.impl.UserServiceImpl;
import com.mychat.chat_backend.exception.UserNotFoundException;
import com.mychat.chat_backend.model.enums.ParticipantRole;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = new User.Builder()
                .username("Testuser")
                .password("1234")
                .email("test@example.com")
                .isAdmin(false)
                .build();

        testUserDto = UserMapper.toUserDto(testUser);
    }

    @Test
    void getUserById_WhenUserExists_ReturnsUserDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        UserDto result = userService.getUserById(1L);
        Assertions.assertEquals(testUserDto.getSummary().getUsername(), result.getSummary().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_WhenUserNotFound_ThrowsUserNotFoundException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(999L);
        });
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void getUserByUsername_WhenUserExists_ReturnsOptionalUserDto() {
        when(userRepository.findByUsername("Testuser")).thenReturn(Optional.of(testUser));
        Optional<UserDto> result = userService.getUserByUsername("Testuser");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(testUserDto.getSummary().getUsername(), result.get().getSummary().getUsername());
        verify(userRepository, times(1)).findByUsername("Testuser");
    }

    @Test
    void getUserByEmail_WhenUserExists_ReturnsOptionalUserDto() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        Optional<UserDto> result = userService.getUserByEmail("test@example.com");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(testUserDto.getSummary().getUsername(), result.get().getSummary().getUsername());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void getAllUsers_ReturnsListOfUserDtos() {
        User user2 = new User.Builder()
                .username("AnotherUser")
                .password("abcd")
                .email("another@example.com")
                .isAdmin(false)
                .build();
        List<User> users = Arrays.asList(testUser, user2);
        when(userRepository.findAll()).thenReturn(users);
        List<UserDto> result = userService.getAllUsers();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(testUserDto.getSummary().getUsername(), result.get(0).getSummary().getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUsersByOnlineStatus_ReturnsListOfUserDtos() {
        testUser.setOnline(true);
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAllByIsOnline(true)).thenReturn(users);
        List<UserDto> result = userService.getUsersByOnlineStatus(true);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(testUserDto.getSummary().getUsername(), result.get(0).getSummary().getUsername());
    }

    @Test
    void getUsersOfRoomWithRoomRepository_ReturnsListOfUserDtos() {
        Long roomId = 10L;
        User secondUser = new User.Builder()
                .username("SecondUser")
                .password("pass")
                .email("secondUserMail@mail.com")
                .isAdmin(false)
                .build();
        UserDto secondUserDto = UserMapper.toUserDto(secondUser);
        Room mockRoom = new Room.Builder()
                .description("Test Room")
                .isPrivate(false)
                .build();
        Set<RoomParticipant> mockParticipants = new HashSet<>();
        RoomParticipant participant1 = new RoomParticipant.Builder()
                .room(mockRoom)
                .user(testUser)
                .role(ParticipantRole.MEMBER)
                .build();
        RoomParticipant participant2 = new RoomParticipant.Builder()
                .room(mockRoom)
                .user(secondUser)
                .role(ParticipantRole.MEMBER)
                .build();
        mockParticipants.add(participant1);
        mockParticipants.add(participant2);
        mockRoom.setParticipants(mockParticipants);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(mockRoom));
        List<UserDto> result = userService.getUsersOfRoomWithRoomRepository(roomId);
        Assertions.assertEquals(2, result.size());
        List<String> usernames = result.stream()
                .map(dto -> dto.getSummary().getUsername())
                .toList();

        Assertions.assertTrue(usernames.contains(testUserDto.getSummary().getUsername()));
        Assertions.assertTrue(usernames.contains(secondUserDto.getSummary().getUsername()));
        verify(roomRepository, times(1)).findById(roomId);
    }

    @Test
    void getUsersByRoomWithUserRepository_ReturnsListOfUserDtos() {
        Long roomId = 20L;
        User secondUser = new User.Builder()
                .username("SecondUser")
                .password("pass")
                .email("secondUserMail@mail.com")
                .isAdmin(false)
                .build();
        UserDto secondUserDto = UserMapper.toUserDto(secondUser);
        List<User> users = Arrays.asList(testUser, secondUser);
        when(userRepository.findAllByRoomParticipationsRoomId(roomId)).thenReturn(users);
        List<UserDto> result = userService.getUsersByRoomWithUserRepository(roomId);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(testUserDto.getSummary().getUsername(), result.get(0).getSummary().getUsername());
        Assertions.assertEquals(secondUserDto.getSummary().getUsername(), result.get(1).getSummary().getUsername());
        verify(userRepository, times(1)).findAllByRoomParticipationsRoomId(roomId);
    }

    @Test
    void createUser_SavesAndReturnsUserDto() {
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(testUser);
        UserCreationDto creationDto = new UserCreationDto();
        creationDto.setUsername("Testuser");
        creationDto.setPassword("1234");
        creationDto.setEmail("test@example.com");
        UserDto result = userService.createUser(creationDto);
        Assertions.assertEquals(testUserDto.getSummary().getUsername(), result.getSummary().getUsername());
        verify(userRepository, times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void updateUser_WhenUserExists_UpdatesAndReturnsUserDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(testUser);
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setEmail("newUpdateMail@mail.com");
        UserDto result = userService.updateUser(updateDto, 1L);
        Assertions.assertEquals(testUserDto.getSummary().getUsername(), result.getSummary().getUsername());
        Assertions.assertEquals("newUpdateMail@mail.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void deleteUser_WhenUserExists_DeletesUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        userService.deleteUser(1L);
        Assertions.assertEquals(0, userRepository.findAll().size());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void setOnlineStatus_UpdatesUserOnlineStatus() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Assertions.assertFalse(testUser.getIsOnline());
        userService.setOnlineStatus(1L, true);
        Assertions.assertTrue(testUser.getIsOnline());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(testUser);
    }
}
