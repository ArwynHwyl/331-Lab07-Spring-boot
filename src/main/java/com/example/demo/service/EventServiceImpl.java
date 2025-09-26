package com.example.demo.service;

import com.example.demo.dao.EventDao;
import com.example.demo.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventDao eventDao;

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
    public Event save(Event event) {
        return eventDao.save(event);
    }
}
