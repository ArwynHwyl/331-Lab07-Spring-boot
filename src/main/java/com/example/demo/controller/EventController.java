package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import com.example.demo.util.SupabaseStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final SupabaseStorageService storageService;

    public EventController(EventService eventService, SupabaseStorageService storageService) {
        this.eventService = eventService;
        this.storageService = storageService;
    }

    @GetMapping("")
    public ResponseEntity<?> getEventLists(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page,
                                           @RequestParam(value = "title", required = false) String title) {
        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<Event> pageOutput;
        if (title == null) {
            pageOutput = eventService.getEvents(perPage,page);
        }else{
            pageOutput =
                    eventService.getEvents(title,PageRequest.of(page-1,perPage));
        }
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
    var dto = com.example.demo.util.LabMapper.INSTANCE.getEventDto(pageOutput.getContent());
    dto.forEach(e -> e.setImages(storageService.toPublicUrls(e.getImages())));
    return new ResponseEntity<>(dto, responseHeader, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }
    var dto = com.example.demo.util.LabMapper.INSTANCE.getEventDto(event);
    dto.setImages(storageService.toPublicUrls(dto.getImages()));
    return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
    Event output = eventService.save(event);
    var dto = com.example.demo.util.LabMapper.INSTANCE.getEventDto(output);
    dto.setImages(storageService.toPublicUrls(dto.getImages()));
    return ResponseEntity.ok(dto);
    }
}
