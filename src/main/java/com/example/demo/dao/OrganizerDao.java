package com.example.demo.dao;

import com.example.demo.entity.Organizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrganizerDao {
    List<Organizer> getOrganizers(Integer perPage, Integer page);
    Organizer getOrganizer(Long id);
    Integer getOrganizerSize();
    Organizer save(Organizer organizer);

    Page<Organizer> getOrganizer(Pageable pageRequest);
    Optional<Organizer> findById(Long id);
}
