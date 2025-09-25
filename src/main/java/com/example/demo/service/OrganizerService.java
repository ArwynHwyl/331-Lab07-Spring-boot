package com.example.demo.service;

import com.example.demo.entity.Organizer;

import java.util.List;

public interface OrganizerService {
    List<Organizer> getOrganizers(Integer perPage, Integer page);
    Organizer getOrganizer(Long id);
    Integer getOrganizerSize();
}
