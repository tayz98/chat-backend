package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    public List<Message> findAllByRoomId(Long roomId);

    public List<Message> findAllByUserId(Long userId);

    public List<Message> findAllByRoomIdAndUserId(Long roomId, Long userId);
}
