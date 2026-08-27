package com.pgvpt.service.impl;

import com.pgvpt.dto.Patrimoine;
import com.pgvpt.entities.PatrimoineEntity;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.mapper.PatrimoineMapper;
import com.pgvpt.repository.PatrimoineRepository;
import com.pgvpt.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicationServiceImpl implements PublicationService {

    private final PatrimoineRepository patrimoineRepository;
    private final PatrimoineMapper patrimoineMapper;

    @Override
    public Patrimoine depublierPatrimoine(UUID id) {
        PatrimoineEntity patrimoine = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));

        patrimoine.setStatut("BROUILLON"); // Or whatever default un-published state is. Let's assume BROUILLON.
        patrimoine.setPublishedAt(null);

        PatrimoineEntity saved = patrimoineRepository.save(patrimoine);
        return patrimoineMapper.toDto(saved);
    }

    @Override
    public Patrimoine publierPatrimoine(UUID id) {
        PatrimoineEntity patrimoine = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));

        patrimoine.setStatut("PUBLIE");
        if (patrimoine.getPublishedAt() == null) {
            patrimoine.setPublishedAt(OffsetDateTime.now());
        }

        PatrimoineEntity saved = patrimoineRepository.save(patrimoine);
        return patrimoineMapper.toDto(saved);
    }
}
