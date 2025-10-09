package com.example.demo.dao;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Profile("db")
@RequiredArgsConstructor
public class EventDaoImpl implements EventDao {
	final EventRepository eventRepository;

	@Override
	public Integer getEventSize() {
		return Math.toIntExact(eventRepository.count());
	}

	@Override
	public Page<Event> getEvents(Integer perSize, Integer page) {
		perSize = perSize == null ? Integer.MAX_VALUE : perSize;
		page = page == null ? 1 : page;
		return eventRepository.findAll(PageRequest.of(Math.max(page - 1, 0), perSize));
	}

	@Override
	public Page<Event> getEvents(String title, Pageable page) {
		return eventRepository.findByTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContainingOrOrganizer_NameIgnoreCaseContaining(title, title, title, page);
	}

	@Override
	public Event getEvent(Long id) {
		return eventRepository.findById(id).orElse(null);
	}

	@Override
	public Event save(Event event) {
		return eventRepository.save(event);
	}
}
