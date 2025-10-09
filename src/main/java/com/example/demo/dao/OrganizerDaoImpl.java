package com.example.demo.dao;

import com.example.demo.entity.Organizer;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("manual")
public class OrganizerDaoImpl implements OrganizerDao {
    List<Organizer> organizerList;

    @PostConstruct
    public void init() {
        organizerList = new ArrayList<>();
        organizerList.add(Organizer.builder()
                .id(1L)
                .name("Kat Laydee")
                .build());
        organizerList.add(Organizer.builder()
                .id(2L)
                .name("Fern Pollin")
                .build());  
        organizerList.add(Organizer.builder()
                .id(3L)
                .name("Carey Wales")
                .build());
        organizerList.add(Organizer.builder()
                .id(4L)
                .name("Dawg Dahd")
                .build());
        organizerList.add(Organizer.builder()
                .id(5L)
                .name("Kahn Opiner")
                .build());
        organizerList.add(Organizer.builder()
                .id(6L)
                .name("Brody Kill")
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

    @Override
    public Organizer save(Organizer organizer) {
        Long nextId = organizerList.isEmpty() ? 1L : organizerList.get(organizerList.size() - 1).getId() + 1;
        organizer.setId(nextId);
        organizerList.add(organizer);
        return organizer;
    }

    @Override
    public Page<Organizer> getOrganizer(Pageable pageRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrganizer'");
    }

    @Override
    public java.util.Optional<Organizer> findById(Long id) {
        return organizerList.stream().filter(o -> o.getId().equals(id)).findFirst();
    }
}
