package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;

import java.util.List;

public interface MessageService {
    MessageDto getMessageById(long messageId);

    List<MessageDto> getMessagesByRoomId(long roomId);

    List<MessageDto> getMessagesByUserId(long userId);

    List<MessageDto> getMessagesByRoomIdAndUserId(long roomId, long userId);

    MessageDto createMessage(MessageCreationDto messageDto);

    MessageDto updateMessage(MessageUpdateDto messageDto, long messageId);

    void deleteMessage(long messageId);

    // TODO: implement more filtering for a search function in the frontend
}
