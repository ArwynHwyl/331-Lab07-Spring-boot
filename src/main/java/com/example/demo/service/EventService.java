package com.example.demo.service;

import com.example.demo.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Page<Event> getEvents(Integer perPage, Integer page);
    Page<Event> getEvents(String title, Pageable pageable);
    Event getEvent(Long id);
    Integer getEventSize();
    Event save(Event event);
}
