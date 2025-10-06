package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;
import com.mychat.chat_backend.mapper.MessageMapper;
import com.mychat.chat_backend.model.Message;
import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.User;
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
        Room room = roomRepository.findById(messageDto.getRoomId()).orElseThrow();
        User user = userRepository.findById(messageDto.getSenderId()).orElseThrow();
        Message newMessage = MessageMapper.toMessage(messageDto, user, room);
        return MessageMapper.toMessageDto(messageRepository.save(newMessage));
    }

    @Override
    public MessageDto updateMessage(MessageUpdateDto messageDto) {
        Message message = messageRepository.findById(messageDto.getMessageId()).orElseThrow();
        Message updatedMessage = MessageMapper.updatedMessage(messageDto, message);
        return MessageMapper.toMessageDto(messageRepository.save(updatedMessage));
    }

    @Override
    public void deleteMessage(long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + messageId));
        messageRepository.delete(message);
    }
}
