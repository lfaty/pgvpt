package com.pgvpt.service.impl;

import com.pgvpt.dto.HoraireOuverture;
import com.pgvpt.service.HoraireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import com.pgvpt.dto.JourSemaine;
import com.pgvpt.entities.HoraireOuvertureEntity;
import com.pgvpt.entities.PatrimoineEntity;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.repository.HoraireRepository;
import com.pgvpt.repository.PatrimoineRepository;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HoraireServiceImpl implements HoraireService {

    private final PatrimoineRepository patrimoineRepository;
    private final HoraireRepository horaireRepository;

    @Override
    public List<HoraireOuverture> getHoraires(UUID id) {
        PatrimoineEntity patrimoine = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));

        return patrimoine.getHoraires().stream()
                .map(this::mapToHoraireOuverture)
                .collect(Collectors.toList());
    }

    @Override
    public List<HoraireOuverture> updateHoraires(UUID id, List<HoraireOuverture> horaires) {
        PatrimoineEntity patrimoine = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));

        // orphanRemoval deletes the old rows when the managed collection is cleared.
        patrimoine.getHoraires().clear();

        // Create new
        List<HoraireOuvertureEntity> newEntities = horaires.stream()
                .map(h -> mapToHoraireOuvertureEntity(h, patrimoine))
                .collect(Collectors.toList());

        patrimoine.getHoraires().addAll(newEntities);
        patrimoineRepository.save(patrimoine);

        return newEntities.stream()
                .map(this::mapToHoraireOuverture)
                .collect(Collectors.toList());
    }

    private HoraireOuverture mapToHoraireOuverture(HoraireOuvertureEntity entity) {
        HoraireOuverture dto = new HoraireOuverture();
        if (entity.getJour() != null) {
            dto.setJour(JourSemaine.fromValue(entity.getJour()));
        }
        dto.setOuvert(entity.getOuvert());
        
        // Handle java.time.LocalTime to String mapping if necessary, or check DTO type
        // Wait, OpenAPI generated DTO might use String for time. Let's see later. 
        // Assuming the DTO uses String for time:
        if (entity.getHeureOuverture() != null) dto.setHeureOuverture(entity.getHeureOuverture().toString());
        if (entity.getHeureFermeture() != null) dto.setHeureFermeture(entity.getHeureFermeture().toString());
        if (entity.getPauseDebut() != null) dto.setPauseDebut(entity.getPauseDebut().toString());
        if (entity.getPauseFin() != null) dto.setPauseFin(entity.getPauseFin().toString());
        
        dto.setSurReservation(entity.getSurReservation());
        dto.setCommentaire(entity.getCommentaire());
        return dto;
    }

    private HoraireOuvertureEntity mapToHoraireOuvertureEntity(HoraireOuverture dto, PatrimoineEntity patrimoine) {
        HoraireOuvertureEntity entity = new HoraireOuvertureEntity();
        if (dto.getJour() != null) {
            entity.setJour(dto.getJour().getValue());
        }
        entity.setOuvert(dto.getOuvert());
        
        if (dto.getHeureOuverture() != null) entity.setHeureOuverture(java.time.LocalTime.parse(dto.getHeureOuverture()));
        if (dto.getHeureFermeture() != null) entity.setHeureFermeture(java.time.LocalTime.parse(dto.getHeureFermeture()));
        if (dto.getPauseDebut() != null) entity.setPauseDebut(java.time.LocalTime.parse(dto.getPauseDebut()));
        if (dto.getPauseFin() != null) entity.setPauseFin(java.time.LocalTime.parse(dto.getPauseFin()));
        
        entity.setSurReservation(dto.getSurReservation());
        entity.setCommentaire(dto.getCommentaire());
        entity.setPatrimoine(patrimoine);
        return entity;
    }
}
