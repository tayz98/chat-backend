package com.mychat.chat_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.RoomParticipant;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.ParticipantRole;

/**
 * Repository interface for RoomParticipant entities.
 */
public interface RoomParticipantRepository extends JpaRepository<RoomParticipant, Long> {

    List<RoomParticipant> findAllByRoom(Room room);

    List<RoomParticipant> findAllByUser(User user);

    Optional<RoomParticipant> findByUserAndRoom(User user, Room room);

    List<RoomParticipant> findAllByRole(ParticipantRole role);

}
