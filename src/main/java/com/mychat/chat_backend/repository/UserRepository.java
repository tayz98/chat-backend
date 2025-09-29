package com.mychat.chat_backend.repository;

import com.mychat.chat_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    public List<User> findAllByIsOnline(Boolean isOnline);

    public List<User> findAllByCurrentRoomsId(Long currentRoomsId);

}
