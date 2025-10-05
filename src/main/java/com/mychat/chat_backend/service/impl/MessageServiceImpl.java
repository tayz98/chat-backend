package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;
import com.mychat.chat_backend.mapper.MessageMapper;
import com.mychat.chat_backend.model.Message;
import com.mychat.chat_backend.repository.MessageRepository;
import com.mychat.chat_backend.repository.RoomRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;

    public MessageServiceImpl() {
    }

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public MessageDto getMessageById(long messageId) {
        return MessageMapper.toMessageDto(messageRepository.findById(messageId).orElseThrow());
    }

    @Override
    public MessageDto createMessage(MessageCreationDto messageDto) {
        Message newMessage = MessageMapper.toMessage(messageDto, userRepository, roomRepository);
        return MessageMapper.toMessageDto(messageRepository.save(newMessage));
    }

    @Override
    public MessageDto updateMessage(MessageUpdateDto messageDto, long messageId) {
        Message updatedMessage = MessageMapper.updatedMessage(messageDto, messageRepository.findById(messageId).orElseThrow());
        return MessageMapper.toMessageDto(messageRepository.save(updatedMessage));
    }

    @Override
    public void deleteMessage(long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + messageId));
        messageRepository.delete(message);
    }
}
