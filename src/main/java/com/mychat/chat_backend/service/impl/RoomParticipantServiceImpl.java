package com.mychat.chat_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.mychat.chat_backend.dto.roomparticipant.RoomParticipantDto;
import com.mychat.chat_backend.repository.RoomParticipantRepository;

@Service
@Validated
public class RoomParticipantServiceImpl {

    RoomParticipantRepository roomParticipantRepository;

    @SuppressWarnings("unused")
    private RoomParticipantServiceImpl() {
    }

    @Autowired
    public RoomParticipantServiceImpl(RoomParticipantRepository roomParticipantRepository) {
        this.roomParticipantRepository = roomParticipantRepository;
    }

    RoomParticipantDto getRoomParticipantById(Long roomParticipantId) {

        return null;
    }

}
