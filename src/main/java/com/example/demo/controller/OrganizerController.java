package com.example.demo.controller;

import com.example.demo.entity.Organizer;
import com.example.demo.entity.OrganizerDTO;
import com.example.demo.util.LabMapper;
import com.example.demo.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/organizers")
@RequiredArgsConstructor
public class OrganizerController {
    private final OrganizerService organizerService;

    @GetMapping
    public ResponseEntity<?> getOrganizers(@RequestParam(value = "_limit", required = false) Integer perPage,
                                                        @RequestParam(value = "_page", required = false) Integer page) {
        if (perPage == null || page == null) {
            return ResponseEntity.ok(LabMapper.INSTANCE.getOrganizerDTO(organizerService.getAllOrganizer()));
        }

        Page<Organizer> p = organizerService.getOrganizer(page, perPage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(p.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(LabMapper.INSTANCE.getOrganizerDTO(p.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizer(@PathVariable("id") Long id) {
        Organizer organizer = organizerService.getOrganizer(id);
        if (organizer != null) {
            return ResponseEntity.ok(LabMapper.INSTANCE.getOrganizerDTO(organizer));
        } else {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "The given id is not found");
        }
    }

    @PostMapping
    public Organizer post(@RequestBody Organizer organizer) {
        return organizerService.save(organizer);
    }
}
