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

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;
import com.mychat.chat_backend.mapper.MessageMapper;
import com.mychat.chat_backend.model.Message;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;
import com.mychat.chat_backend.repository.MessageRepository;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.service.impl.MessageServiceImpl;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    private Message testMessage;
    private MessageDto testMessageDto;
    private User testUser;
    private Room testRoom;
    @SuppressWarnings("unused")
    private RoomParticipant testRoomParticipant;

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
                .user(testUser)
                .room(testRoom)
                .role(ParticipantRole.MEMBER)
                .build();
        testMessage = new Message.Builder()
                .content("Hello, World!")
                .user(testUser)
                .room(testRoom)
                .build();
        testMessageDto = MessageMapper.toMessageDto(testMessage);
    }

    @Test
    void getMessageById_WhenMessageExists_ReturnsMessageDto() {
        when(messageRepository.findById(testMessage.getId())).thenReturn(Optional.of(testMessage));
        MessageDto result = messageService.getMessageById(testMessage.getId());
        Assertions.assertEquals(testMessageDto, result);
        verify(messageRepository, times(1)).findById(testMessage.getId());
    }

    @Test
    void getMessagesByRoomId_WhenRoomExists_ReturnsListOfMessageDtos() {
        Message testMessage2 = new Message.Builder()
                .content("Second message")
                .user(testUser)
                .room(testRoom)
                .build();
        List<Message> messages = List.of(testMessage, testMessage2);
        when(roomRepository.findById(testRoom.getId())).thenReturn(Optional.of(testRoom));
        when(messageRepository.findAllByRoomId(testRoom.getId())).thenReturn(messages);
        List<MessageDto> result = messageService.getMessagesByRoomId(testRoom.getId());
        Assertions.assertEquals(2, result.size());
        verify(roomRepository, times(1)).findById(testRoom.getId());
        verify(messageRepository, times(1)).findAllByRoomId(testRoom.getId());
    }

    @Test
    void getMessagesByUserId_WhenUserExists_ReturnsListOfMessageDtos() {
        Message testMessage3 = new Message.Builder()
                .content("Third message")
                .user(testUser)
                .room(testRoom)
                .build();
        List<Message> messages = List.of(testMessage, testMessage3);
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(messageRepository.findAllByUserId(testUser.getId())).thenReturn(messages);
        List<MessageDto> result = messageService.getMessagesByUserId(testUser.getId());
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Third message", result.get(1).getContent());
        verify(userRepository, times(1)).findById(testUser.getId());
        verify(messageRepository, times(1)).findAllByUserId(testUser.getId());
    }

    @Test
    void getMessagesByRoomIdAndUserId_WhenRoomAndUserExist_ReturnsListOfMessageDtos() {
        Message testMessage4 = new Message.Builder()
                .content("Fourth message")
                .user(testUser)
                .room(testRoom)
                .build();
        List<Message> messages = List.of(testMessage, testMessage4);
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(roomRepository.findById(testRoom.getId())).thenReturn(Optional.of(testRoom));
        when(messageRepository.findAllByRoomIdAndUserId(testRoom.getId(), testUser.getId())).thenReturn(messages);
        List<MessageDto> result = messageService.getMessagesByRoomIdAndUserId(testRoom.getId(), testUser.getId());
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Fourth message", result.get(1).getContent());
        verify(userRepository, times(1)).findById(testUser.getId());
        verify(roomRepository, times(1)).findById(testRoom.getId());
        verify(messageRepository, times(1)).findAllByRoomIdAndUserId(testRoom.getId(), testUser.getId());
    }

    @Test
    void createMessage_SavesAndReturnsMessageDto() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(roomRepository.findById(testRoom.getId())).thenReturn(Optional.of(testRoom));
        when(messageRepository.save(ArgumentMatchers.any(Message.class))).thenReturn(testMessage);
        MessageCreationDto creationDto = new MessageCreationDto(testUser.getId(), testRoom.getId(), "Hello, World!");
        MessageDto result = messageService.createMessage(creationDto);
        Assertions.assertEquals(testMessageDto, result);
        verify(userRepository, times(1)).findById(testUser.getId());
        verify(roomRepository, times(1)).findById(testRoom.getId());
        verify(messageRepository, times(1)).save(ArgumentMatchers.any(Message.class));
    }

    @Test
    void updateMessage_WhenMessageExists_UpdatesAndReturnsMessageDto() {
        when(messageRepository.findById(testMessage.getId())).thenReturn(Optional.of(testMessage));
        when(messageRepository.save(ArgumentMatchers.any(Message.class))).thenReturn(testMessage);
        MessageUpdateDto updateDto = new MessageUpdateDto("Updated content");
        MessageDto result = messageService.updateMessage(updateDto, testMessage.getId());
        Assertions.assertEquals("Updated content", result.getContent());
        Assertions.assertNotEquals(testMessageDto, result);
        verify(messageRepository, times(1)).findById(testMessage.getId());
        verify(messageRepository, times(1)).save(ArgumentMatchers.any(Message.class));
    }

    @Test
    void deleteMessage_WhenMessageExists_DeletesMessage() {
        when(messageRepository.findById(testMessage.getId())).thenReturn(Optional.of(testMessage));
        messageService.deleteMessage(testMessage.getId());
        verify(messageRepository, times(1)).findById(testMessage.getId());
        verify(messageRepository, times(1)).delete(testMessage);
    }
}