package com.mychat.chat_backend.mapper;

import com.mychat.chat_backend.dto.message.*;
import com.mychat.chat_backend.dto.user.UserSummaryDto;
import com.mychat.chat_backend.model.Message;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.User;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
/**
 * Mapper class for converting between Message entities and DTOs.
 */
public class MessageMapper {

    private MessageMapper() {
    }

    /**
     * Converts a Message entity to a MessageDto.
     *
     * @param message the Message entity to convert
     * @return the corresponding MessageDto
     */
    public static MessageDto toMessageDto(Message message) {
        Long id = message.getId();
        String content = message.getContent();
        Long senderId = message.getUser().getId();
        String senderUsername = message.getUser().getUsername();
        String senderAvatar = message.getUser().getAvatarUrl();
        UserSummaryDto sender = new UserSummaryDto(senderId, senderUsername, senderAvatar);
        Long roomId = message.getRoom().getId();
        Instant timestamp = message.getCreatedOn();
        Instant editedTimestamp = message.getUpdatedOn();
        Boolean isDeleted = message.getDeleted();
        return new MessageDto(id, content, sender, roomId, timestamp, editedTimestamp, isDeleted);
    }

    /**
     * Converts a MessageCreationDto to a Message entity.
     *
     * @param messageCreationDto the MessageCreationDto to convert
     * @param sender             the User who is sending the message
     * @param room               the Room where the message is being sent
     * @return the corresponding Message entity
     */
    public static Message toMessage(MessageCreationDto messageCreationDto, User sender, Room room) {
        return new Message.Builder()
                .content(messageCreationDto.getContent())
                .user(sender)
                .room(room)
                .build();
    }

    /**
     * Updates an existing Message entity with data from a MessageUpdateDto.
     *
     * @param messageDto         the MessageUpdateDto containing updated data
     * @param messageToBeUpdated the existing Message entity to update
     * @return the updated Message entity
     */
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
