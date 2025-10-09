package com.example.demo.util;

import com.example.demo.entity.Event;
import com.example.demo.entity.EventDTO;
import com.example.demo.entity.EventSummaryDTO;
import com.example.demo.entity.Organizer;
import com.example.demo.entity.OrganizerDTO;
import com.example.demo.entity.OrganizerOwnEventsDTO;
import com.example.demo.entity.Participant;
import com.example.demo.entity.ParticipantSummaryDTO;
import com.example.demo.entity.ParticipantWithEventsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    EventSummaryDTO getEventSummaryDTO(Event event);

    List<EventSummaryDTO> getEventSummaryDTO(List<Event> events);

    ParticipantSummaryDTO getParticipantSummaryDTO(Participant participant);

    List<ParticipantSummaryDTO> getParticipantSummaryDTO(List<Participant> participants);

    @Mapping(target = "events", source = "eventHistory")
    ParticipantWithEventsDTO getParticipantWithEventsDTO(Participant participant);

    List<ParticipantWithEventsDTO> getParticipantWithEventsDTO(List<Participant> participants);
}
