package com.example.demo.dao;

import com.example.demo.entity.Organizer;

import java.util.List;

public interface OrganizerDao {
    List<Organizer> getOrganizers(Integer perPage, Integer page);
    Organizer getOrganizer(Long id);
    Integer getOrganizerSize();
}
