package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.Room;
import com.mychat.chat_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Room repository
 * Contains methods to access rooms in the database
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Find all rooms owned by a user
     *
     * @param owner User/owner of the rooms
     * @return List of rooms owned by the given user/owner
     */
    public List<Room> findAllByOwner(User owner);

    /**
     * Find all rooms in which a user is a participant
     *
     * @param participantId User id of the participant
     * @return List of rooms in which the given user id is a participant
     */
    public List<Room> findAllByParticipantsId(Long participantId);

    /**
     * Find the room in which a message is sent
     *
     * @param messageId Message id of the message
     * @return Room in which the given message id is sent
     */
    Room findByMessagesId(Long messageId);
}
