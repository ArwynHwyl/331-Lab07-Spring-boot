package com.example.demo.service;

import com.example.demo.entity.Event;

import java.util.List;

public interface EventService {
    List<Event> getEvents(Integer perPage, Integer page);
    Event getEvent(Long id);
    Integer getEventSize();
}
