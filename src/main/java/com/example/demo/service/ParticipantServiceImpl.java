package com.example.demo.service;

import com.example.demo.dao.ParticipantDao;
import com.example.demo.entity.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantDao participantDao;

    @Override
    public List<Participant> getParticipants(Integer perPage, Integer page) {
        return participantDao.getParticipants(perPage, page);
    }

    @Override
    public Participant getParticipant(Long id) {
        return participantDao.getParticipant(id);
    }

    @Override
    public Integer getParticipantSize() {
        return participantDao.getParticipantSize();
    }

    @Override
    public Participant save(Participant participant) {
        return participantDao.save(participant);
    }
}
