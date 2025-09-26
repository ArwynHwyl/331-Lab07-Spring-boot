package com.example.demo.service;

import com.example.demo.entity.Event;
import org.springframework.data.domain.Page;

public interface EventService {
    Page<Event> getEvents(Integer perPage, Integer page);
    Event getEvent(Long id);
    Integer getEventSize();
    Event save(Event event);
}
