package com.example.demo.service;

import com.example.demo.dao.OrganizerDao;
import com.example.demo.entity.Organizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizerServiceImpl implements OrganizerService {
    @Autowired
    OrganizerDao organizerDao;

    @Override
    public List<Organizer> getOrganizers(Integer perPage, Integer page) {
        return organizerDao.getOrganizers(perPage, page);
    }

    @Override
    public Organizer getOrganizer(Long id) {
        return organizerDao.getOrganizer(id);
    }

    @Override
    public Integer getOrganizerSize() {
        return organizerDao.getOrganizerSize();
    }
}
