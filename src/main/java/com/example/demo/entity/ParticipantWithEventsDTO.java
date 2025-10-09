package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantWithEventsDTO {
    private Long id;
    private String name;
    private String telNo;
    private List<EventSummaryDTO> events;
}
