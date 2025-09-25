package com.example.demo.service;

import com.example.demo.dao.EventDao;
import com.example.demo.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventDao eventDao;

    @Override
    public List<Event> getEvents(Integer perPage, Integer page) {
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
}
