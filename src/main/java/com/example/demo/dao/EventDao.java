package com.example.demo.dao;

import com.example.demo.entity.Event;

import java.util.List;

public interface EventDao {
    List<Event> getEvents(Integer perSize, Integer page);
    Event getEvent(Long id);
    Integer getEventSize();
}
