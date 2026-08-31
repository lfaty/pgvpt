package com.pgvpt.controller;

import com.pgvpt.api.RechercheApi;
import com.pgvpt.dto.CategoriePatrimoine;
import com.pgvpt.dto.PatrimoineSummary;
import com.pgvpt.dto.TypePatrimoine;
import com.pgvpt.service.RechercheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RechercheController implements RechercheApi {

    private final RechercheService rechercheService;

    @Override
    public ResponseEntity<List<PatrimoineSummary>> rechercherPatrimoines(String q, TypePatrimoine type, CategoriePatrimoine categorie, String region, String departement, String commune) {
        List<PatrimoineSummary> result = rechercheService.rechercherPatrimoines(q, type, categorie, region, departement, commune);
        return ResponseEntity.ok(result);
    }
}
