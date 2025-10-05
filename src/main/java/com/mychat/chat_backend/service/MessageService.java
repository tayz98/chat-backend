package com.mychat.chat_backend.service;

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;

public interface MessageService {
    MessageDto getMessageById(long messageId);

    MessageDto createMessage(MessageCreationDto messageDto);

    MessageDto updateMessage(MessageUpdateDto messageDto, long messageId);

    void deleteMessage(long messageId);
}
