package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;
import com.mychat.chat_backend.model.Message;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MessageMapper {
    public static MessageDto toMessageDto(Message message) {
        long id = message.getId();
        String content = message.getContent();
        long senderId = message.getUser().getId();
        String senderUsername = message.getUser().getUsername();
        long roomId = message.getRoom().getId();
        Instant timestamp = message.getTimestamp();
        Instant editedTimestamp = message.getEditedTimestamp();
        return new MessageDto(id, content, senderId, senderUsername, roomId, timestamp, editedTimestamp);
    }

    public static Message toMessage(MessageCreationDto messageCreationDto, UserRepository userRepository, RoomRepository roomRepository) {
        return new Message(messageCreationDto.getContent(), userRepository.findById(messageCreationDto.getSenderId()).orElseThrow(), roomRepository.findById(messageCreationDto.getRoomId()).orElseThrow());
    }

    public static Message updatedMessage(MessageUpdateDto messageDto, Message message) {
        if (messageDto.getContent() != null) {
            message.setContent(messageDto.getContent());
        }
        message.setEditedTimestamp(Instant.now());
        return message;
    }
}
