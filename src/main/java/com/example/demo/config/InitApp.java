package com.example.demo.config;

import com.example.demo.entity.Event;
import com.example.demo.entity.Organizer;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("db")
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
        final EventRepository eventRepository;
        final OrganizerRepository organizerRepository;

    @Override
        public void onApplicationEvent(@org.springframework.lang.NonNull ApplicationReadyEvent applicationReadyEvent) {
        // Seed Events (only when table is empty to avoid duplicates)
        if (eventRepository.count() == 0) {
            eventRepository.save(Event.builder()
                    .category("Academic")
                    .title("Midterm Exam")
                    .description("A time for taking the exam")
                    .location("CAMT Building")
                    .date("3rd Sept")
                    .time("3.00-4.00 pm.")
                    .petAllowed(false)
                    .organizer("CAMT")
                    .build());

            eventRepository.save(Event.builder()
                    .category("Academic")
                    .title("Commencement Day")
                    .description("A time for celebration")
                    .location("CMU Convention hall")
                    .date("21th Jan")
                    .time("8.00am-4.00 pm.")
                    .petAllowed(false)
                    .organizer("CMU")
                    .build());

            eventRepository.save(Event.builder()
                    .category("Cultural")
                    .title("Loy Krathong")
                    .description("A time for Krathong")
                    .location("Ping River")
                    .date("12th Nov")
                    .time("8.00-10.00 pm.")
                    .petAllowed(false)
                    .organizer("Chiang Mai")
                    .build());

            eventRepository.save(Event.builder()
                    .category("Cultural")
                    .title("Songkran")
                    .description("Let's Play Water")
                    .location("Chiang Mai Moat")
                    .date("13th April")
                    .time("10.00am - 6.00 pm.")
                    .petAllowed(true)
                    .organizer("Chiang Mai Municipality")
                    .build());
        }

        // Seed Organizers (only when table is empty)
        if (organizerRepository.count() == 0) {
            organizerRepository.save(Organizer.builder().name("Kat Laydee").address("123 Meow Town").build());
            organizerRepository.save(Organizer.builder().name("Fern Pollin").address("456 Flora City").build());
            organizerRepository.save(Organizer.builder().name("Carey Wales").address("789 Playa Del Carmen").build());
            organizerRepository.save(Organizer.builder().name("Dawg Dahd").address("101 Woof Town").build());
        }
    }
}
