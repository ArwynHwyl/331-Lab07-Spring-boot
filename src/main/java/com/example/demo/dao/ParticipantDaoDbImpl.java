package com.example.demo.dao;

import com.example.demo.entity.Participant;
import com.example.demo.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("db")
@RequiredArgsConstructor
public class ParticipantDaoDbImpl implements ParticipantDao {
    private final ParticipantRepository participantRepository;

    @Override
    public List<Participant> getParticipants(Integer perPage, Integer page) {
        List<Participant> participants = participantRepository.findAll();
        perPage = perPage == null ? participants.size() : perPage;
        page = page == null ? 1 : page;
        int from = Math.max((page - 1) * perPage, 0);
        int to = Math.min(from + perPage, participants.size());
        return from > participants.size() ? List.of() : participants.subList(from, to);
    }

    @Override
    public Participant getParticipant(Long id) {
        return participantRepository.findById(id).orElse(null);
    }

    @Override
    public Integer getParticipantSize() {
        return Math.toIntExact(participantRepository.count());
    }

    @Override
    public Participant save(Participant participant) {
        return participantRepository.save(participant);
    }
}
