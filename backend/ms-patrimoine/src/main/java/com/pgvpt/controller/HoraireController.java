package com.pgvpt.controller;

import com.pgvpt.api.HorairesApi;
import com.pgvpt.dto.HoraireOuverture;
import com.pgvpt.service.PatrimoineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HoraireController implements HorairesApi {
    private final PatrimoineService service;
    @Override
    public ResponseEntity<List<HoraireOuverture>> getHoraires(UUID id) {
        return ResponseEntity.ok(service.getHoraires(id));
    }

    @Override
    public ResponseEntity<List<HoraireOuverture>> updateHoraires(UUID id, List<HoraireOuverture> horaireOuverture) {
        return ResponseEntity.ok(service.updateHoraires(id, horaireOuverture));
    }
}
