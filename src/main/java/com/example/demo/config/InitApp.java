package com.example.demo.config;

import com.example.demo.entity.Event;
import com.example.demo.entity.Organizer;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.OrganizerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
@Profile("db")
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
        final EventRepository eventRepository;
        final OrganizerRepository organizerRepository;

    @Override
        public void onApplicationEvent(@org.springframework.lang.NonNull ApplicationReadyEvent applicationReadyEvent) {
        Organizer org1,org2,org3;
           org1 = organizerRepository.save(Organizer.builder().name("CAMT").build());
           org2 = organizerRepository.save(Organizer.builder().name("CMU").build());
           org3 = organizerRepository.save(Organizer.builder().name("ChiangMai").build());
           Event tempEvent;
           tempEvent = eventRepository.save(Event.builder()
                   .title("Academic")
                   .description("Midterm Exam")
                   .petAllowed(false)
                   .build());
                tempEvent.setOrganizer(org1);
                eventRepository.save(tempEvent);
                  tempEvent = eventRepository.save(Event.builder()
                         .title("Sport Day")
                         .description("Football Match")
                         .petAllowed(true)
                         .build());
                tempEvent.setOrganizer(org2);
                eventRepository.save(tempEvent);
                  tempEvent = eventRepository.save(Event.builder()
                         .title("Songkran")
                         .description("Water Festival")
                         .petAllowed(true)
                         .build());
                tempEvent.setOrganizer(org3);
                eventRepository.save(tempEvent);
    }
}
