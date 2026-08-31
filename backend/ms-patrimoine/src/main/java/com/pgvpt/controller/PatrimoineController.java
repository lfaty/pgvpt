package com.pgvpt.controller;

import com.pgvpt.api.PatrimoinesApi;
import com.pgvpt.dto.Patrimoine;
import com.pgvpt.dto.PatrimoineCreate;
import com.pgvpt.dto.PatrimoineUpdate;
import com.pgvpt.dto.PagePatrimoine;
import com.pgvpt.dto.CategoriePatrimoine;
import com.pgvpt.dto.TypePatrimoine;
import com.pgvpt.dto.StatutPatrimoine;
import com.pgvpt.dto.EtatConservation;
import com.pgvpt.service.PatrimoineService;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatrimoineController implements PatrimoinesApi {

    private final PatrimoineService patrimoineService;

    @Override
    public ResponseEntity<Patrimoine> createPatrimoine(PatrimoineCreate patrimoineCreate) {
        Patrimoine result = patrimoineService.create(patrimoineCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Patrimoine> getPatrimoine(UUID id) {
        Patrimoine result = patrimoineService.getPatrimoine(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deletePatrimoine(UUID id) {
        patrimoineService.deletePatrimoine(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Patrimoine> updatePatrimoine(UUID id, PatrimoineUpdate patrimoineUpdate) {
        Patrimoine result = patrimoineService.updatePatrimoine(id, patrimoineUpdate);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<PagePatrimoine> getPatrimoines(Integer page, Integer size, String sort, 
        CategoriePatrimoine categorie, TypePatrimoine type, String region, 
        String departement, String commune, StatutPatrimoine statut, 
        EtatConservation etatConservation, Boolean accessiblePublic, 
        Boolean inscritUnesco, Boolean classePatrimoine, String q) {
        
        PagePatrimoine result = patrimoineService.getPatrimoines(page, size, sort, categorie, type, region, departement, commune, statut, etatConservation, accessiblePublic, inscritUnesco, classePatrimoine, q);
        return ResponseEntity.ok(result);
    }
}
