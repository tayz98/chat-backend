package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;
import com.mychat.chat_backend.model.Message;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MessageMapper {

    private MessageMapper() {
    }

    public static MessageDto toMessageDto(Message message) {
        Long id = message.getId();
        String content = message.getContent();
        Long senderId = message.getUser().getId();
        String senderUsername = message.getUser().getUsername();
        Long roomId = message.getRoom().getId();
        Instant timestamp = message.getCreatedOn();
        Instant editedTimestamp = message.getUpdatedOn();
        Boolean isDeleted = message.getDeleted();
        return new MessageDto(id, content, senderId, senderUsername, roomId, timestamp, editedTimestamp, isDeleted);
    }

    public static Message toMessage(MessageCreationDto messageCreationDto, User sender, Room room) {
        return new Message.Builder()
                .content(messageCreationDto.getContent())
                .user(sender)
                .room(room)
                .build();
    }

    public static Message updatedMessage(MessageUpdateDto messageDto, Message messageToBeUpdated) {
        if (messageDto.getContent() != null) {
            messageToBeUpdated.setContent(messageDto.getContent());
        }
        if (messageDto.getIsDeleted() != null) {
            messageToBeUpdated.setDeleted(messageDto.getIsDeleted());
        }
        return messageToBeUpdated;
    }
}
