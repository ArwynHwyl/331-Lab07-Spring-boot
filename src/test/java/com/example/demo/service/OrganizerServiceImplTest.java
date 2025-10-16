package com.example.demo.service;

import com.example.demo.entity.Organizer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrganizerServiceImplTest {

    @Autowired
    OrganizerService organizerService;

    @Test
    void getAllOrganizer_returnsAllRows() {
        List<Organizer> all = organizerService.getAllOrganizer();
        System.out.println("Organizers: " + all.size());
        assertThat(all).hasSizeGreaterThan(1);
    }
}
