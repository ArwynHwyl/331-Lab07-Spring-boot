package com.example.demo.service;

import com.example.demo.dao.EventDao;
import com.example.demo.dao.OrganizerDao;
import com.example.demo.entity.Event;
import com.example.demo.entity.Organizer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;
    private final OrganizerDao organizerDao;

    @Override
    public Page<Event> getEvents(Integer perPage, Integer page) {
        return eventDao.getEvents(perPage, page);
    }

    @Override
    public Event getEvent(Long id) {
        return eventDao.getEvent(id);
    }

    @Override
    public Integer getEventSize() {
        return eventDao.getEventSize();
    }

    @Override
    @Transactional
    public Event save(Event event) {
        Organizer organizer = organizerDao.findById(event.getOrganizer().getId()).orElse(null);
        event.setOrganizer(organizer);
        if (organizer != null) {
            organizer.getOwnEvents().add(event);
        }
        return eventDao.save(event);
    }
}
