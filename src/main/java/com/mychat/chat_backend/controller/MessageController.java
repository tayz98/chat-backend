package com.mychat.chat_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;
import com.mychat.chat_backend.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/messages")
/*
 * MessageController will handle all message-related API endpoints, including
 * CRUD operations for messages.
 */
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // GET
    @GetMapping("")
    public ResponseEntity<List<MessageDto>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    // GET all messages that exist
    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    // GET all messages in a room
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<MessageDto>> getMessagesByRoomId(@PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok(messageService.getMessagesByRoomId(roomId));
    }

    // GET all messages sent by a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageDto>> getMessagesByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(messageService.getMessagesByUserId(userId));
    }

    // GET all messages in a room sent by a specific user
    @GetMapping("/room/{roomId}/user/{userId}")
    public ResponseEntity<List<MessageDto>> getMessagesByRoomIdAndUserId(@PathVariable("roomId") Long roomId,
            @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(messageService.getMessagesByRoomIdAndUserId(roomId, userId));
    }

    // POST
    @PostMapping("")
    public ResponseEntity<MessageDto> createMessage(@Valid @RequestBody MessageCreationDto messageDto) {
        return ResponseEntity.ok(messageService.createMessage(messageDto));
    }

    // PUT
    @PostMapping("/{id}")
    public ResponseEntity<MessageDto> updateMessage(@Valid @RequestBody MessageUpdateDto messageDto,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(messageService.updateMessage(messageDto, id));
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("id") Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
