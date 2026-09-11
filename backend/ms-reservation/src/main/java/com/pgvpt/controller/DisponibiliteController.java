package com.pgvpt.controller;

import com.pgvpt.api.DisponibiliteApi;
import com.pgvpt.dto.DisponibiliteResponse;
import com.pgvpt.dto.TypeRessource;
import com.pgvpt.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DisponibiliteController implements DisponibiliteApi {

    private final ReservationService service;

    @Override
    public ResponseEntity<DisponibiliteResponse> checkAvailability(TypeRessource typeRessource, UUID ressourceId, LocalDate date, Integer nombrePersonnes) {
        com.pgvpt.enums.TypeRessource t = typeRessource != null ? com.pgvpt.enums.TypeRessource.valueOf(typeRessource.name()) : null;
        return ResponseEntity.ok(service.checkAvailability(t, ressourceId, date.atStartOfDay(ZoneOffset.UTC).toInstant(), nombrePersonnes));
    }
}
