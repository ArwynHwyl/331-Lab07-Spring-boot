package com.example.demo.controller;

import com.example.demo.entity.Participant;
import com.example.demo.service.ParticipantService;
import com.example.demo.util.LabMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participants")
public class ParticipantController {
    private final ParticipantService participantService;

    @GetMapping
    public ResponseEntity<?> getParticipants(
            @RequestParam(value = "_limit", required = false) Integer perPage,
            @RequestParam(value = "_page", required = false) Integer page
    ) {
        List<Participant> participants = participantService.getParticipants(perPage, page);
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-total-count", String.valueOf(participantService.getParticipantSize()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getParticipantWithEventsDTO(participants), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipant(@PathVariable("id") Long id) {
        Participant participant = participantService.getParticipant(id);
        if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found");
        }
        return ResponseEntity.ok(LabMapper.INSTANCE.getParticipantWithEventsDTO(participant));
    }
}
