package com.pgvpt.controller;

import com.pgvpt.api.HorairesApi;
import com.pgvpt.dto.HoraireOuverture;
import com.pgvpt.service.HoraireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HoraireController implements HorairesApi {

    private final HoraireService horaireService;

    @Override
    public ResponseEntity<List<HoraireOuverture>> getHoraires(UUID id) {
        List<HoraireOuverture> result = horaireService.getHoraires(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<HoraireOuverture>> updateHoraires(UUID id, List<HoraireOuverture> horaireOuverture) {
        List<HoraireOuverture> result = horaireService.updateHoraires(id, horaireOuverture);
        return ResponseEntity.ok(result);
    }
}
