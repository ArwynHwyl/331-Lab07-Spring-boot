package com.example.demo.controller;

import com.example.demo.entity.Organizer;
import com.example.demo.entity.OrganizerDTO;
import com.example.demo.util.LabMapper;
import com.example.demo.util.SupabaseStorageService;
import com.example.demo.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

 

@RestController
@RequestMapping("/organizers")
@RequiredArgsConstructor
public class OrganizerController {
    private final OrganizerService organizerService;
    private final SupabaseStorageService storageService;

    @GetMapping
    public ResponseEntity<?> getOrganizers(@RequestParam(value = "_limit", required = false) Integer perPage,
                                                        @RequestParam(value = "_page", required = false) Integer page) {
        if (perPage == null || page == null) {
            var dto = LabMapper.INSTANCE.getOrganizerDTO(organizerService.getAllOrganizer());
            dto.forEach(this::decorateOrganizerDto);
            return ResponseEntity.ok(dto);
        }

        Page<Organizer> p = organizerService.getOrganizer(page, perPage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(p.getTotalElements()));
        var dto = LabMapper.INSTANCE.getOrganizerDTO(p.getContent());
        dto.forEach(this::decorateOrganizerDto);
        return ResponseEntity.ok().headers(headers).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizer(@PathVariable("id") Long id) {
        Organizer organizer = organizerService.getOrganizer(id);
        if (organizer != null) {
            var dto = LabMapper.INSTANCE.getOrganizerDTO(organizer);
            decorateOrganizerDto(dto);
            return ResponseEntity.ok(dto);
        } else {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "The given id is not found");
        }
    }

    @PostMapping
    public Organizer post(@RequestBody Organizer organizer) {
        return organizerService.save(organizer);
    }

    private void decorateOrganizerDto(OrganizerDTO organizerDTO) {
        organizerDTO.setImage(storageService.toPublicUrl(organizerDTO.getImage()));
        organizerDTO.getOwnEvents()
                .forEach(ev -> ev.setImages(storageService.toPublicUrls(ev.getImages())));
    }
}
