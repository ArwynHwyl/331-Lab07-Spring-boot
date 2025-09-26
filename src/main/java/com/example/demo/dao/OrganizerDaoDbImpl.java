package com.example.demo.dao;

import com.example.demo.entity.Organizer;
import com.example.demo.repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("db")
@RequiredArgsConstructor
public class OrganizerDaoDbImpl implements OrganizerDao {
    private final OrganizerRepository organizerRepository;

    @Override
    public List<Organizer> getOrganizers(Integer perPage, Integer page) {
        // Simple approach: fetch all then window (could be optimized to Page later)
        List<Organizer> list = organizerRepository.findAll();
        perPage = perPage == null ? list.size() : perPage;
        page = page == null ? 1 : page;
        int from = Math.max((page - 1) * perPage, 0);
        int to = Math.min(from + perPage, list.size());
        return from > list.size() ? List.of() : list.subList(from, to);
    }

    @Override
    public Organizer getOrganizer(Long id) {
        return organizerRepository.findById(id).orElse(null);
    }

    @Override
    public Integer getOrganizerSize() {
        return Math.toIntExact(organizerRepository.count());
    }

    public Organizer save(Organizer organizer) {
        return organizerRepository.save(organizer);
    }
}
