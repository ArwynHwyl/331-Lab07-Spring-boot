package com.example.demo.dao;

import com.example.demo.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventDao {
    Page<Event> getEvents(Integer perSize, Integer page);
    Page<Event> getEvents(String name, Pageable page);
    Event getEvent(Long id);
    Integer getEventSize();
    Event save(Event event);
}
