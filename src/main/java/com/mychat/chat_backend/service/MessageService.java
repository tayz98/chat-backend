package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;

import java.util.List;

public interface MessageService {
    MessageDto getMessageById(Long messageId);

    List<MessageDto> getMessagesByRoomId(Long roomId);

    List<MessageDto> getMessagesByUserId(Long userId);

    List<MessageDto> getMessagesByRoomIdAndUserId(Long roomId, Long userId);

    MessageDto createMessage(MessageCreationDto messageDto);

    MessageDto updateMessage(MessageUpdateDto messageDto, Long messageId);

    void deleteMessage(Long messageId);

    // TODO: implement more filtering for a search function in the frontend
}
