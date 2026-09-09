package com.pgvpt.controller;

import com.pgvpt.api.DisponibiliteApi;
import com.pgvpt.dto.DisponibiliteResponse;
import com.pgvpt.dto.TypeRessource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DisponibiliteController implements DisponibiliteApi {

    @Override
    public ResponseEntity<DisponibiliteResponse> checkAvailability(TypeRessource typeRessource, UUID ressourceId, LocalDate date, Integer nombrePersonnes) {
        return null;
    }
}
