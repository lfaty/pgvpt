package com.pgvpt.media.controller;

import com.pgvpt.api.*;
import com.pgvpt.dto.*;
import com.pgvpt.media.enums.TypeMediaMetier;
import com.pgvpt.media.mapper.MediaMapper;
import com.pgvpt.media.service.MediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
public class MediaController implements MdiasApi{
    private final MediaService service;
    private final MediaMapper mapper;

    public MediaController(MediaService service, MediaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Media> createMedia(MediaCreate mediaCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(mediaCreate));
    }

    @Override
    public ResponseEntity<Void> deleteMedia(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Media> getMedia(UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<List<Media>> listMedias(UUID patrimoineId, TypeMedia type) {
        return ResponseEntity.ok(service.findByPatrimoineIdAndType(patrimoineId, type));
    }

    @Override
    public ResponseEntity<Media> updateMedia(UUID id, MediaCreate mediaCreate) {
        return ResponseEntity.ok(service.update(id, mediaCreate));
    }

}
