package com.mychat.chat_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;
import com.mychat.chat_backend.dto.room.RoomDto;
import com.mychat.chat_backend.dto.user.UserDto;
import com.mychat.chat_backend.mapper.MessageMapper;
import com.mychat.chat_backend.mapper.UserMapper;
import com.mychat.chat_backend.model.Message;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;
import com.mychat.chat_backend.repository.MessageRepository;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.repository.RoomParticipantRepository;
import com.mychat.chat_backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
/*
 * Integration tests for MessageController.
 * Tests end-to-end functionality including HTTP requests and database
 * interactions.
 */
class MessageControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoomRepository roomRepository;

        @Autowired
        private RoomParticipantRepository roomParticipantRepository;

        @Autowired
        private MessageRepository messageRepository;

        private RoomDto room;
        private UserDto user1;
        private UserDto user2;
        private MessageDto message1;
        private MessageDto message2;

        @Transactional
        @BeforeEach
        void setUp() {
                messageRepository.deleteAll();
                roomParticipantRepository.deleteAll();
                roomRepository.deleteAll();
                userRepository.deleteAll();

                Room roomEntity = new Room.Builder()
                                .description("Test Room")
                                .isPrivate(false)
                                .password("")
                                .build();
                roomEntity = roomRepository.save(roomEntity);

                User userEntity1 = new User.Builder()
                                .username("user1")
                                .password("password1")
                                .email("user1@example.com")
                                .build();
                userEntity1 = userRepository.save(userEntity1);

                User userEntity2 = new User.Builder()
                                .username("user2")
                                .password("password2")
                                .email("user2@example.com")
                                .build();
                userEntity2 = userRepository.save(userEntity2);

                RoomParticipant participant1 = new RoomParticipant(
                                roomEntity, userEntity1, ParticipantRole.MEMBER);
                roomParticipantRepository.save(participant1);

                RoomParticipant participant2 = new RoomParticipant(
                                roomEntity, userEntity2, ParticipantRole.MEMBER);
                roomParticipantRepository.save(participant2);

                Message msg1 = new Message.Builder()
                                .content("Hello from user1")
                                .user(userEntity1)
                                .room(roomEntity)
                                .build();
                msg1 = messageRepository.save(msg1);

                Message msg2 = new Message.Builder()
                                .content("Hello from user2")
                                .user(userEntity2)
                                .room(roomEntity)
                                .build();
                msg2 = messageRepository.save(msg2);

                room = new RoomDto();
                room.setId(roomEntity.getId());
                room.setDescription(roomEntity.getDescription());

                message1 = MessageMapper.toMessageDto(msg1);
                message2 = MessageMapper.toMessageDto(msg2);

                user1 = UserMapper.toUserDto(userEntity1);
                user2 = UserMapper.toUserDto(userEntity2);
        }

        @Transactional
        @AfterEach
        void tearDown() {
                messageRepository.deleteAll();
                roomParticipantRepository.deleteAll();
                roomRepository.deleteAll();
                userRepository.deleteAll();
        }

        @Test
        void getAllMessages_EndToEnd() throws Exception {
                mockMvc.perform(get("/api/messages"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                                .andExpect(jsonPath("$[*].content", hasItems("Hello from user1", "Hello from user2")));
        }

        @Test
        void getMessageById_EndToEnd() throws Exception {
                mockMvc.perform(get("/api/messages/{id}", message1.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id", is(message1.getId().intValue())))
                                .andExpect(jsonPath("$.content", is("Hello from user1")))
                                .andExpect(jsonPath("$.sender.id", is(user1.getSummary().getId().intValue())))
                                .andExpect(jsonPath("$.roomId", is(room.getId().intValue())))
                                .andExpect(jsonPath("$.timestamp", notNullValue()));
        }

        @Test
        void getMessageById_NotFound() throws Exception {
                mockMvc.perform(get("/api/messages/{id}", 99999L))
                                .andExpect(status().isNotFound());
        }

        @Test
        void getMessagesByRoomId_EndToEnd() throws Exception {
                mockMvc.perform(get("/api/messages/room/{roomId}", room.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                                .andExpect(jsonPath("$[*].roomId", everyItem(is(room.getId().intValue()))));
        }

        @Test
        void getMessagesByRoomId_EmptyList() throws Exception {
                mockMvc.perform(get("/api/messages/room/{roomId}", 99999L))
                                .andExpect(status().isNotFound());
        }

        @Test
        void getMessagesByUserId_EmptyList() throws Exception {
                mockMvc.perform(get("/api/messages/user/{userId}", 99999L))
                                .andExpect(status().isNotFound());
        }

        @Test
        void getMessagesByRoomIdAndUserId_EmptyList() throws Exception {
                mockMvc.perform(get("/api/messages/room/{roomId}/user/{userId}", 99999L, user1.getSummary().getId()))
                                .andExpect(status().isNotFound());
        }

        @Test
        void getMessagesByUserId_EndToEnd() throws Exception {
                mockMvc.perform(get("/api/messages/user/{userId}", user1.getSummary().getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                                .andExpect(jsonPath("$[0].content", is("Hello from user1")))
                                .andExpect(jsonPath("$[0].sender.id", is(user1.getSummary().getId().intValue())));
        }

        @Test
        void getMessagesByRoomIdAndUserId_EndToEnd() throws Exception {
                mockMvc.perform(get("/api/messages/room/{roomId}/user/{userId}", room.getId(),
                                user1.getSummary().getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                                .andExpect(jsonPath("$[0].content", is("Hello from user1")))
                                .andExpect(jsonPath("$[0].sender.id", is(user1.getSummary().getId().intValue())))
                                .andExpect(jsonPath("$[0].roomId", is(room.getId().intValue())));
        }

        @Test
        void createMessage_EndToEnd() throws Exception {
                MessageCreationDto creationDto = new MessageCreationDto(user1.getSummary().getId(), room.getId(),
                                "New message!");

                String response = mockMvc.perform(post("/api/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(creationDto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", is("New message!")))
                                .andExpect(jsonPath("$.sender.id", is(user1.getSummary().getId().intValue())))
                                .andExpect(jsonPath("$.roomId", is(room.getId().intValue())))
                                .andExpect(jsonPath("$.isDeleted", is(false)))
                                .andExpect(jsonPath("$.timestamp", notNullValue()))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                MessageDto createdMessage = objectMapper.readValue(response, MessageDto.class);

                mockMvc.perform(get("/api/messages/{id}", createdMessage.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", is("New message!")));
        }

        @Test
        void createMessage_ValidationError_BlankContent() throws Exception {
                MessageCreationDto creationDto = new MessageCreationDto(user1.getSummary().getId(), room.getId(), "");

                mockMvc.perform(post("/api/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(creationDto)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createMessage_ValidationError_ContentTooLong() throws Exception {
                String longContent = "a".repeat(256);
                MessageCreationDto creationDto = new MessageCreationDto(user1.getSummary().getId(), room.getId(),
                                longContent);

                mockMvc.perform(post("/api/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(creationDto)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createMessage_ValidationError_NullSenderId() throws Exception {
                MessageCreationDto creationDto = new MessageCreationDto(null, room.getId(), "Test message");

                mockMvc.perform(post("/api/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(creationDto)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void createMessage_ValidationError_NullRoomId() throws Exception {
                MessageCreationDto creationDto = new MessageCreationDto(user1.getSummary().getId(), null,
                                "Test message");

                mockMvc.perform(post("/api/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(creationDto)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void updateMessage_EndToEnd() throws Exception {
                MessageUpdateDto updateDto = new MessageUpdateDto();
                updateDto.setContent("Updated content");

                mockMvc.perform(post("/api/messages/{id}", message1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", is("Updated content")))
                                .andExpect(jsonPath("$.editedTimestamp", notNullValue()));

                mockMvc.perform(get("/api/messages/{id}", message1.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", is("Updated content")));
        }

        @Test
        void updateMessage_MarkAsDeleted_EndToEnd() throws Exception {
                MessageUpdateDto updateDto = new MessageUpdateDto();
                updateDto.setIsDeleted(true);

                mockMvc.perform(post("/api/messages/{id}", message1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.isDeleted", is(true)));
        }

        @Test
        void updateMessage_NotFound() throws Exception {
                MessageUpdateDto updateDto = new MessageUpdateDto();
                updateDto.setContent("Updated content");

                mockMvc.perform(post("/api/messages/{id}", 99999L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)))
                                .andExpect(status().isNotFound());
        }

        @Test
        void deleteMessage_EndToEnd() throws Exception {
                mockMvc.perform(post("/api/messages/delete/{id}", message1.getId()))
                                .andExpect(status().isNoContent());

                mockMvc.perform(get("/api/messages/{id}", message1.getId()))
                                .andExpect(status().isNotFound());
        }

        @Test
        void deleteMessage_NotFound() throws Exception {
                mockMvc.perform(post("/api/messages/delete/{id}", 99999L))
                                .andExpect(status().isNotFound());
        }

        @Test
        void createUpdateAndDeleteMessage_EndToEnd() throws Exception {
                MessageCreationDto creationDto = new MessageCreationDto(user1.getSummary().getId(), room.getId(),
                                "Test message");

                String createResponse = mockMvc.perform(post("/api/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(creationDto)))
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                MessageDto createdMessage = objectMapper.readValue(createResponse, MessageDto.class);

                MessageUpdateDto updateDto = new MessageUpdateDto();
                updateDto.setContent("Updated test message");

                mockMvc.perform(post("/api/messages/{id}", createdMessage.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", is("Updated test message")));

                mockMvc.perform(post("/api/messages/delete/{id}", createdMessage.getId()))
                                .andExpect(status().isNoContent());

                mockMvc.perform(get("/api/messages/{id}", createdMessage.getId()))
                                .andExpect(status().isNotFound());
        }
}
