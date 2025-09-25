package com.example.demo.repository;

import com.example.demo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	// Override to return List instead of Iterable (JpaRepository already does this, but kept for clarity)
	List<Event> findAll();
}

