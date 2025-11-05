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

    /**
     * 
     * Finds all participations in a given room.
     *
     * @param room the chat room
     * @return a list of RoomParticipant entities
     */
    List<RoomParticipant> findAllByRoom(Room room);

    /**
     * 
     * Finds all participations for a given user.
     *
     * @param user the user
     * @return a list of RoomParticipant entities
     */
    List<RoomParticipant> findAllByUser(User user);

    /**
     * 
     * Finds a participant by user and room.
     *
     * @param user the user
     * @param room the chat room
     * @return an Optional containing the RoomParticipant if found, or empty if not
     *         found
     */
    Optional<RoomParticipant> findByUserAndRoom(User user, Room room);

    /**
     * 
     * Finds all RoomParticipants with a specific role.
     *
     * @param role the participant role
     * @return a list of RoomParticipant entities
     */
    List<RoomParticipant> findAllByRole(ParticipantRole role);

    /**
     * 
     * Finds all RoomParticipants with a specific role in a given room.
     *
     * @param role the participant role
     * @param room the chat room
     * @return a list of RoomParticipant entities
     */
    List<RoomParticipant> findAllByRoleAndRoom(ParticipantRole role, Room room);

}
