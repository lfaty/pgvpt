package com.pgvpt.controller;

import com.pgvpt.api.PhotosApi;
import com.pgvpt.dto.PhotoPrincipale;
import com.pgvpt.service.PatrimoineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PhotoController implements PhotosApi {

    private final PatrimoineService service;

    @Override
    public ResponseEntity<Void> deletePhotoPrincipale(UUID id) {
        service.deletePhotoPrincipale(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PhotoPrincipale> getPhotoPrincipale(UUID id) {
        PhotoPrincipale photo = service.getPhotoPrincipale(id);
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(photo);
    }

    @Override
    public ResponseEntity<Void> updatePhotoPrincipale(UUID id, @Valid PhotoPrincipale photoPrincipale) {
        service.updatePhotoPrincipale(id, photoPrincipale);
        return ResponseEntity.ok().build();
    }
}
