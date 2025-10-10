package com.mychat.chat_backend.service.impl;

import com.mychat.chat_backend.dto.message.MessageCreationDto;
import com.mychat.chat_backend.dto.message.MessageDto;
import com.mychat.chat_backend.dto.message.MessageUpdateDto;
import com.mychat.chat_backend.exception.MessageNotFoundException;
import com.mychat.chat_backend.exception.RoomNotFoundException;
import com.mychat.chat_backend.exception.UserNotFoundException;
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

import java.util.List;

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
        return MessageMapper.toMessageDto(messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new));
    }

    @Override
    public List<MessageDto> getMessagesByRoomId(long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        List<Message> messages = room.getMessages();
        return messages.stream().map(MessageMapper::toMessageDto).toList();
    }

    @Override
    public List<MessageDto> getMessagesByUserId(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Message> messages = messageRepository.findAllByUserId(user.getId());
        return messages.stream().map(MessageMapper::toMessageDto).toList();
    }

    @Override
    public List<MessageDto> getMessagesByRoomIdAndUserId(long roomId, long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        List<Message> messages = messageRepository.findAllByRoomIdAndUserId(room.getId(), user.getId());
        return messages.stream().map(MessageMapper::toMessageDto).toList();
    }

    @Override
    public MessageDto createMessage(MessageCreationDto messageDto) {
        Room room = roomRepository.findById(messageDto.getRoomId()).orElseThrow(RoomNotFoundException::new);
        User user = userRepository.findById(messageDto.getSenderId()).orElseThrow(UserNotFoundException::new);
        Message newMessage = MessageMapper.toMessage(messageDto, user, room);
        return MessageMapper.toMessageDto(messageRepository.save(newMessage));
    }

    @Override
    public MessageDto updateMessage(MessageUpdateDto messageDto, long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        Message updatedMessage = MessageMapper.updatedMessage(messageDto, message);
        updatedMessage.setUpdatedOn();
        return MessageMapper.toMessageDto(messageRepository.save(updatedMessage));
    }

    @Override
    public void deleteMessage(long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        messageRepository.delete(message);
    }
}
