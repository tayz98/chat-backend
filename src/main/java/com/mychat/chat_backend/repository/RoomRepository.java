package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Room repository
 * Contains methods to access rooms in the database
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

}
