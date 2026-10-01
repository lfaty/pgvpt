package com.pgvpt.controller;

import com.pgvpt.api.ConservationApi;
import com.pgvpt.dto.Conservation;
import com.pgvpt.service.PatrimoineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ConservationController implements ConservationApi {
    private final PatrimoineService service;

    @Override
    public ResponseEntity<Conservation> getConservation(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getConservation(id));
    }

    @Override
    public ResponseEntity<Conservation> updateConservation( @PathVariable UUID id,  @Valid @RequestBody Conservation conservation) {
        return ResponseEntity.ok(service.updateConservation(id, conservation));
    }
}
