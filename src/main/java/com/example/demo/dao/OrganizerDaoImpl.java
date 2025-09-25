package com.example.demo.dao;

import com.example.demo.entity.Organizer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizerDaoImpl implements OrganizerDao {
    List<Organizer> organizerList;

    @PostConstruct
    public void init() {
        organizerList = new ArrayList<>();
        organizerList.add(Organizer.builder()
                .id(1L)
                .name("Kat Laydee")
                .address("123 Meow Town")
                .build());
        organizerList.add(Organizer.builder()
                .id(2L)
                .name("Fern Pollin")
                .address("456 Flora City")
                .build());
        organizerList.add(Organizer.builder()
                .id(3L)
                .name("Carey Wales")
                .address("789 Playa Del Carmen")
                .build());
        organizerList.add(Organizer.builder()
                .id(4L)
                .name("Dawg Dahd")
                .address("101 Woof Town")
                .build());
        organizerList.add(Organizer.builder()
                .id(5L)
                .name("Kahn Opiner")
                .address("202 Tin City")
                .build());
        organizerList.add(Organizer.builder()
                .id(6L)
                .name("Brody Kill")
                .address("303 Highway 50")
                .build());
    }

    @Override
    public List<Organizer> getOrganizers(Integer perPage, Integer page) {
        perPage = perPage == null ? organizerList.size() : perPage;
        page = page == null ? 1 : page;
        int firstIndex = (page - 1) * perPage;
        List<Organizer> output = new ArrayList<>();
        for (int i = firstIndex; i < firstIndex + perPage; i++) {
            if (i >= organizerList.size()) {
                break;
            }
            output.add(organizerList.get(i));
        }
        return output;
    }

    @Override
    public Organizer getOrganizer(Long id) {
        return organizerList.stream()
                .filter(organizer -> organizer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Integer getOrganizerSize() {
        return organizerList.size();
    }
}
