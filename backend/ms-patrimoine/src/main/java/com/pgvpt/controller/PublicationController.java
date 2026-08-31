package com.pgvpt.controller;

import com.pgvpt.api.PublicationApi;
import com.pgvpt.dto.Patrimoine;
import com.pgvpt.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PublicationController implements PublicationApi {

    private final PublicationService publicationService;

    @Override
    public ResponseEntity<Patrimoine> depublierPatrimoine(UUID id) {
        Patrimoine result = publicationService.depublierPatrimoine(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Patrimoine> publierPatrimoine(UUID id) {
        Patrimoine result = publicationService.publierPatrimoine(id);
        return ResponseEntity.ok(result);
    }
}
