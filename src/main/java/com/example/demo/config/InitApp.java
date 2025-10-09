package com.example.demo.config;

import com.example.demo.entity.Event;
import com.example.demo.entity.Organizer;
import com.example.demo.entity.Participant;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.OrganizerRepository;
import com.example.demo.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Profile("db")
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final ParticipantRepository participantRepository;

    @Override
    @Transactional
    public void onApplicationEvent(@org.springframework.lang.NonNull ApplicationReadyEvent applicationReadyEvent) {
        List<Event> seededEvents = new ArrayList<>();
        if (eventRepository.count() == 0) {
            seededEvents = seedOrganizersWithEvents();
        }

        if (organizerRepository.count() == 0) {
            seedOrganizersOnly();
        }

        if (participantRepository.count() == 0) {
            List<Event> availableEvents = seededEvents.isEmpty() ? eventRepository.findAll() : seededEvents;
            seedParticipants(availableEvents);
        }
    }

    private List<Event> seedOrganizersWithEvents() {
        Organizer camt = ensureOrganizer("CAMT");
        Organizer cmu = ensureOrganizer("CMU");
        Organizer chiangMai = ensureOrganizer("Chiang Mai");

        Event midterm = saveEventWithOrganizer(
                "Academic",
                "Midterm Exam",
                "Midterm examination schedule briefing for second-year CAMT students.",
                "Innovation Building, Room 501",
                "October 15, 2024",
                "09.00-11.00 a.m.",
                false,
                camt
        );

        Event showcase = saveEventWithOrganizer(
                "Academic",
                "Project Showcase",
                "Capstone teams present prototypes to faculty advisors and industry mentors.",
                "Creative Media Studio, CAMT",
                "October 18, 2024",
                "13.00-16.00 p.m.",
                false,
                camt
        );

        Event loyKrathong = saveEventWithOrganizer(
                "Cultural",
                "Loy Krathong Night",
                "Create krathongs together and celebrate by the campus lake.",
                "Ang Kaew Lake, CMU",
                "November 27, 2024",
                "18.00-21.00 p.m.",
                false,
                cmu
        );

        Event songkran = saveEventWithOrganizer(
                "Cultural",
                "Songkran",
                "Celebrate the Thai New Year with water festivities and traditional performances.",
                "Tha Phae Gate, Chiang Mai",
                "April 13, 2025",
                "08.00-22.00 p.m.",
                true,
                chiangMai
        );

        return List.of(midterm, showcase, loyKrathong, songkran);
    }

    private void seedOrganizersOnly() {
        ensureOrganizer("CAMT");
        ensureOrganizer("CMU");
        ensureOrganizer("Chiang Mai");
    }

    private void seedParticipants(List<Event> events) {
        if (events.isEmpty()) {
            return;
        }
        Map<String, Event> eventsByTitle = events.stream()
                .collect(Collectors.toMap(Event::getTitle, event -> event, (existing, replacement) -> existing));

        Event midterm = resolveEvent("Midterm Exam", eventsByTitle);
        Event showcase = resolveEvent("Project Showcase", eventsByTitle);
        Event loyKrathong = resolveEvent("Loy Krathong Night", eventsByTitle);
        Event songkran = resolveEvent("Songkran", eventsByTitle);

        if (midterm == null || showcase == null || loyKrathong == null || songkran == null) {
            return;
        }

        saveParticipant(
                "Arisa Nithan",
                "081-111-2233",
                List.of(midterm, showcase, loyKrathong)
        );

        saveParticipant(
                "Benjarat Kitti",
                "082-202-3434",
                List.of(midterm, showcase, songkran)
        );

        saveParticipant(
                "Chaiyan Phon",
                "083-303-4545",
                List.of(midterm, loyKrathong, songkran)
        );

        saveParticipant(
                "Darin Siri",
                "084-404-5656",
                List.of(showcase, loyKrathong, songkran)
        );

        saveParticipant(
                "Ekkachai M.",
                "085-505-6767",
                List.of(midterm, showcase, loyKrathong, songkran)
        );
    }

    private Event resolveEvent(String title, Map<String, Event> eventsByTitle) {
        return eventsByTitle.getOrDefault(title, eventRepository.findByTitle(title).orElse(null));
    }

    private Organizer ensureOrganizer(String name) {
        Organizer organizer = organizerRepository.findByName(name)
                .orElseGet(() -> organizerRepository.save(
                        Organizer.builder()
                                .name(name)
                                .ownEvents(new ArrayList<>())
                                .build()
                ));
        if (organizer.getOwnEvents() == null) {
            organizer.setOwnEvents(new ArrayList<>());
        }
        return organizer;
    }

    private Event saveEventWithOrganizer(String category,
                                         String title,
                                         String description,
                                         String location,
                                         String date,
                                         String time,
                                         boolean petAllowed,
                                         Organizer organizer) {
        Event event = Event.builder()
                .category(category)
                .title(title)
                .description(description)
                .location(location)
                .date(date)
                .time(time)
                .petAllowed(petAllowed)
                .organizer(organizer)
                .build();

        Event savedEvent = eventRepository.save(event);
        organizer.getOwnEvents().add(savedEvent);
        return savedEvent;
    }

    private Participant saveParticipant(String name, String telNo, List<Event> attendedEvents) {
        Participant participant = Participant.builder()
                .name(name)
                .telNo(telNo)
                .eventHistory(new ArrayList<>(attendedEvents))
                .build();

        attendedEvents.forEach(event -> event.getParticipants().add(participant));

        Participant savedParticipant = participantRepository.save(participant);
        eventRepository.saveAll(attendedEvents);
        return savedParticipant;
    }
}
