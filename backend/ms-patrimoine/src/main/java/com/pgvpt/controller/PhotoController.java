package com.pgvpt.controller;

import com.pgvpt.api.PhotosApi;
import com.pgvpt.dto.Photo;
import com.pgvpt.dto.PhotoCreate;
import com.pgvpt.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PhotoController implements PhotosApi {

    private final PhotoService photoService;

    @Override
    public ResponseEntity<Photo> addPhoto(UUID id, PhotoCreate photoCreate) {
        Photo result = photoService.addPhoto(id, photoCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Void> deletePhoto(UUID id, UUID photoId) {
        photoService.deletePhoto(id, photoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Photo>> getPhotos(UUID id) {
        List<Photo> result = photoService.getPhotos(id);
        return ResponseEntity.ok(result);
    }
}
