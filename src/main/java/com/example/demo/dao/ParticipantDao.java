package com.example.demo.dao;

import com.example.demo.entity.Participant;

import java.util.List;

public interface ParticipantDao {
    List<Participant> getParticipants(Integer perPage, Integer page);
    Participant getParticipant(Long id);
    Integer getParticipantSize();
    Participant save(Participant participant);
}
