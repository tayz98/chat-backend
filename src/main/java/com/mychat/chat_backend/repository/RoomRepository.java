package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    public List<Room> findAllByOwnerId(Long userId);

    public List<Room> findAllByParticipantsId(Long participantId);
    
    Room findByMessagesId(Long messageId);
}
