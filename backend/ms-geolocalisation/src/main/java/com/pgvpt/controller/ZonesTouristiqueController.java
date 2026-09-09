package com.pgvpt.controller;

import com.pgvpt.api.*;
import com.pgvpt.dto.*;
import com.pgvpt.mapper.ZoneTouristiqueApiMapper;
import com.pgvpt.model.ZoneTouristiqueEntity;
import com.pgvpt.service.ZoneTouristiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ZonesTouristiqueController implements ZoneTouristiqueApi {

    private final ZoneTouristiqueService zoneTouristiqueService;
    private final ZoneTouristiqueApiMapper mapper;

    @Override
    public ResponseEntity<ZoneTouristique> createZoneTouristique(ZoneTouristiqueCreate zoneTouristiqueCreate) {
        ZoneTouristiqueEntity entity = mapper.toEntity(zoneTouristiqueCreate);
        ZoneTouristiqueEntity created = zoneTouristiqueService.createZoneTouristique(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Void> deleteZoneTouristique(UUID id) {
        zoneTouristiqueService.deleteZoneTouristique(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ZoneTouristique> getZoneTouristique(UUID id) {
        return ResponseEntity.ok(mapper.toApi(zoneTouristiqueService.getZoneTouristiqueById(id)));
    }

    @Override
    public ResponseEntity<PageZoneTouristique> getZonesTouristiques(Integer page, Integer size) {
        int pageIndex = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 20;
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("nom").ascending());

        Page<ZoneTouristiqueEntity> pageEntity = zoneTouristiqueService.getZonesTouristiques(pageable);
        List<ZoneTouristique> dtos = pageEntity.getContent().stream().map(mapper::toApi).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pageEntity.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(pageEntity.getTotalPages()));

        PageZoneTouristique pageResponse = new PageZoneTouristique();
        pageResponse.setContent(dtos);
        pageResponse.setPage(pageEntity.getNumber());
        pageResponse.setSize(pageEntity.getSize());
        pageResponse.setTotalElements(pageEntity.getTotalElements());
        pageResponse.setTotalPages(pageEntity.getTotalPages());
        pageResponse.setFirst(pageEntity.isFirst());
        pageResponse.setLast(pageEntity.isLast());

        return ResponseEntity.ok().headers(headers).body(pageResponse);
    }

    @Override
    public ResponseEntity<ZoneTouristique> updateZoneTouristique(UUID id, ZoneTouristiqueUpdate zoneTouristiqueUpdate) {
        ZoneTouristiqueEntity entity = mapper.toEntity(zoneTouristiqueUpdate);
        ZoneTouristiqueEntity updated = zoneTouristiqueService.updateZoneTouristique(id, entity);
        return ResponseEntity.ok(mapper.toApi(updated));
    }
}

