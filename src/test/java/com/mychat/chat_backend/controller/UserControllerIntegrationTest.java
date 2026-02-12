package com.mychat.chat_backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mychat.chat_backend.dto.user.UserCreationDto;
import com.mychat.chat_backend.dto.user.UserDto;
import com.mychat.chat_backend.dto.user.UserUpdateDto;
import com.mychat.chat_backend.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
/*
 * Integration tests for UserController.
 * Tests end-to-end functionality including HTTP requests and database
 * interactions.
 */
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void createAndGetUser_EndToEnd() throws Exception {
        UserCreationDto creationDto = new UserCreationDto("john", "john@example.com", "password123", false);

        // Create user
        String response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.summary.username", is("john")))
                .andExpect(jsonPath("$.email", is("john@example.com")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto createdUser = objectMapper.readValue(response, UserDto.class);

        // Get user
        mockMvc.perform(get("/api/users/{id}", createdUser.getSummary().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summary.username", is("john")))
                .andExpect(jsonPath("$.summary.id", is(createdUser.getSummary().getId().intValue())))
                .andExpect(jsonPath("$.email", is("john@example.com")))
                .andExpect(jsonPath("$.isAdmin", is(false)))
                .andExpect(jsonPath("$.isOnline", is(notNullValue())))
                .andExpect(jsonPath("$.created", notNullValue()));
    }

    @Test
    void getUser_NotFound() throws Exception {
        // Attempt to get a non-existing user
        mockMvc.perform(get("/api/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_EndToEnd() throws Exception {
        // First, create a user to update
        UserCreationDto creationDto = new UserCreationDto("john", "john@example.com", "password123", false);
        // Create the user via the controller
        String response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDto = objectMapper.readValue(response, UserDto.class);

        // Now, update the user's email
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setEmail("newMail@mail.coom");
        // Perform the update via the controller
        mockMvc.perform(put("/api/users/{id}", userDto.getSummary().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("newMail@mail.coom")));
    }

    @Test
    void deleteUser_EndToEnd() throws Exception {
        // First, create a user to delete
        UserCreationDto creationDto = new UserCreationDto("jane", "jane@example.com", "password123", false);
        // Create the user via the controller
        String response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserDto userDto = objectMapper.readValue(response, UserDto.class);
        // Delete the user
        mockMvc.perform(delete("/api/users/{id}", userDto.getSummary().getId()))
                .andExpect(status().isNoContent());
        // Verify the user is deleted
        mockMvc.perform(get("/api/users/{id}", userDto.getSummary().getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsers_EndToEnd() throws Exception {
        // Create multiple users
        UserCreationDto user1 = new UserCreationDto("user1", "user1@example.com", "password123", false);
        UserCreationDto user2 = new UserCreationDto("user2", "user2@example.com", "password123", false);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isCreated());

        // Get all users
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].summary.username", is("user1")))
                .andExpect(jsonPath("$[1].summary.username", is("user2")));
    }
}