package com.example.demo.service;

import com.example.demo.dao.OrganizerDao;
import com.example.demo.entity.Organizer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizerServiceImpl implements OrganizerService {
    private final OrganizerDao organizerDao;

    @Override
    public List<Organizer> getAllOrganizer() {
        return organizerDao.getOrganizers(null, null);
    }

    @Override
    public Page<Organizer> getOrganizer(Integer page, Integer pageSize) {
        int p = page == null ? 1 : page;
        int size = pageSize == null ? Integer.MAX_VALUE : pageSize;
        return organizerDao.getOrganizer(PageRequest.of(Math.max(p - 1, 0), size));
    }

    @Override
    public Organizer getOrganizer(Long id) {
        return organizerDao.getOrganizer(id);
    }

    @Override
    public Integer getOrganizerSize() {
        return organizerDao.getOrganizerSize();
    }

    @Override
    public Organizer save(Organizer organizer) {
        return organizerDao.save(organizer);
    }
}
