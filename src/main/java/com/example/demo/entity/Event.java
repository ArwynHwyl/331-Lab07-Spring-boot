package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {
    private Long id;
    private String category;
    private String title;
    private String description;
    private String location;
    private String date;
    private String time;
    private Boolean petAllowed;
    private String organizer;
}
