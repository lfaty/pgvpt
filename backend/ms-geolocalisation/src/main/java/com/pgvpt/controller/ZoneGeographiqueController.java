package com.pgvpt.controller;

import com.dev.pgvpt.api.ZoneGeographiqueApi;
import com.dev.pgvpt.api.model.PageZoneGeographique;
import com.dev.pgvpt.api.model.ZoneGeographique;
import com.dev.pgvpt.api.model.ZoneGeographiqueCreate;
import com.dev.pgvpt.api.model.ZoneGeographiqueUpdate;
import com.pgvpt.mapper.ZoneGeographiqueApiMapper;
import com.pgvpt.model.ZoneGeographiqueEntity;
import com.pgvpt.service.ZoneGeographiqueService;
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
public class ZoneGeographiqueController implements ZoneGeographiqueApi {

    private final ZoneGeographiqueService zoneGeographiqueService;
    private final ZoneGeographiqueApiMapper mapper;

    @Override
    public ResponseEntity<ZoneGeographique> createZoneGeographique(ZoneGeographiqueCreate zoneGeographiqueCreate) {
        ZoneGeographiqueEntity entity = mapper.toEntity(zoneGeographiqueCreate);
        ZoneGeographiqueEntity created = zoneGeographiqueService.createZoneGeographique(entity, zoneGeographiqueCreate.getRegionIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Void> deleteZoneGeographique(UUID id) {
        zoneGeographiqueService.deleteZoneGeographique(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ZoneGeographique> getZoneGeographique(UUID id) {
        return ResponseEntity.ok(mapper.toApi(zoneGeographiqueService.getZoneGeographiqueById(id)));
    }

    @Override
    public ResponseEntity<PageZoneGeographique> getZonesGeographiques(Integer page, Integer size) {
        int pageIndex = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 20;
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("nom").ascending());

        Page<ZoneGeographiqueEntity> pageEntity = zoneGeographiqueService.getZonesGeographiques(pageable);
        List<ZoneGeographique> dtos = pageEntity.getContent().stream().map(mapper::toApi).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pageEntity.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(pageEntity.getTotalPages()));

        PageZoneGeographique pageResponse = new PageZoneGeographique();
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
    public ResponseEntity<ZoneGeographique> updateZoneGeographique(UUID id, ZoneGeographiqueUpdate zoneGeographiqueUpdate) {
        ZoneGeographiqueEntity entity = mapper.toEntity(zoneGeographiqueUpdate);
        ZoneGeographiqueEntity updated = zoneGeographiqueService.updateZoneGeographique(id, entity, zoneGeographiqueUpdate.getRegionIds());
        return ResponseEntity.ok(mapper.toApi(updated));
    }
}

