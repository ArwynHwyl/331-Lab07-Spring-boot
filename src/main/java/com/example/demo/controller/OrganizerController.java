package com.example.demo.controller;

import com.example.demo.entity.Organizer;
import com.example.demo.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
        List<Organizer> output = organizerService.getOrganizers(perPage, page);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(organizerService.getOrganizerSize()));
        return new ResponseEntity<>(output, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizer(@PathVariable("id") Long id) {
        Organizer organizer = organizerService.getOrganizer(id);
        if (organizer != null) {
            return ResponseEntity.ok(organizer);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }
    }
}
