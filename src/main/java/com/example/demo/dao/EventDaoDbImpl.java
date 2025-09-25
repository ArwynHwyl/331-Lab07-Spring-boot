package com.example.demo.dao;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("db")
@RequiredArgsConstructor
public class EventDaoDbImpl implements EventDao {
	final EventRepository eventRepository;

	@Override
	public Integer getEventSize() {
		return Math.toIntExact(eventRepository.count());
	}

	@Override
	public List<Event> getEvents(Integer perSize, Integer page) {
		List<Event> events = eventRepository.findAll();

		perSize = perSize == null ? events.size() : perSize;
		page = page == null ? 1 : page;
		int firstIndex = (page - 1) * perSize;
		List<Event> output = events.subList(firstIndex, firstIndex + perSize);
		return output;
	}

	@Override
	public Event getEvent(Long id) {
		return eventRepository.findById(id).orElse(null);
	}
}
