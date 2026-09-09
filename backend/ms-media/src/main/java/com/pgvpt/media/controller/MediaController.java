package com.pgvpt.media.controller;

import com.pgvpt.api.*;
import com.pgvpt.dto.*;
import com.pgvpt.media.mapper.MediaApiMapper;
import com.pgvpt.media.service.MediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MediaController implements MdiasApi{
    private final MediaService service;
    private final MediaApiMapper mapper;

    public MediaController(MediaService service, MediaApiMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Media> createMedia(MediaCreate mediaCreate) {
        var entity = mapper.toEntity(mediaCreate);
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Void> deleteMedia(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Media> getMedia(UUID id) {
        return ResponseEntity.ok(mapper.toApi(service.getById(id)));
    }

    @Override
    public ResponseEntity<List<Media>> listMedias(UUID patrimoineId, TypeMedia type) {
        com.pgvpt.media.enums.TypeMedia serviceTypeMedia = mapper.toEntityEnum(type);

        return ResponseEntity.ok(
                mapper.toApi(service.findByPatrimoineIdAndType(patrimoineId, serviceTypeMedia))
        );
    }

}
