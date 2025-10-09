package com.example.demo.service;

import com.example.demo.entity.Participant;

import java.util.List;

public interface ParticipantService {
    List<Participant> getParticipants(Integer perPage, Integer page);
    Participant getParticipant(Long id);
    Integer getParticipantSize();
    Participant save(Participant participant);
}
