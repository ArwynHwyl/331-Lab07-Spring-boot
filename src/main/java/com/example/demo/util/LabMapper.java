package com.example.demo.util;

import com.example.demo.entity.Event;
import com.example.demo.entity.EventDTO;
import com.example.demo.entity.Organizer;
import com.example.demo.entity.OrganizerDTO;
import com.example.demo.entity.OrganizerOwnEventsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    EventDTO getEventDto(Event event);

    List<EventDTO> getEventDto(List<Event> events);

    OrganizerDTO getOrganizerDTO(Organizer organizer);

    List<OrganizerDTO> getOrganizerDTO(List<Organizer> organizers);

    OrganizerOwnEventsDTO getOrganizerOwnEventsDTO(Event event);

    List<OrganizerOwnEventsDTO> getOrganizerOwnEventsDTO(List<Event> events);
}
