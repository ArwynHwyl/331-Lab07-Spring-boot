package com.example.demo.service;

import com.example.demo.entity.Organizer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrganizerService {
    List<Organizer> getAllOrganizer();
    Page<Organizer> getOrganizer(Integer page, Integer pageSize);
    Organizer getOrganizer(Long id);
    Integer getOrganizerSize();
    Organizer save(Organizer organizer);
}
