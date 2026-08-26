package com.pgvpt.service.impl;

import com.pgvpt.dto.Patrimoine;
import com.pgvpt.dto.PatrimoineCreate;
import com.pgvpt.entities.PatrimoineEntity;
import com.pgvpt.mapper.PatrimoineMapper;
import com.pgvpt.repository.PatrimoineRepository;
import com.pgvpt.service.PatrimoineService;
import lombok.RequiredArgsConstructor;
import com.pgvpt.dto.PatrimoineUpdate;
import com.pgvpt.dto.PagePatrimoine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatrimoineServiceImpl implements PatrimoineService {

    private final PatrimoineRepository patrimoineRepository;
    private final PatrimoineMapper patrimoineMapper;

    @Override
    public Patrimoine create(PatrimoineCreate patrimoineCreate) {
        // 1. Convertir le DTO d'entrée en entité JPA
        PatrimoineEntity entity = patrimoineMapper.toEntity(patrimoineCreate);

        // 2. Sauvegarder en base de données
        PatrimoineEntity saved = patrimoineRepository.save(entity);

        // 3. Convertir l'entité sauvegardée en DTO de réponse
        return patrimoineMapper.toDto(saved);
    }

    @Override
    public Patrimoine getPatrimoine(UUID id) {
        PatrimoineEntity entity = patrimoineRepository.findById(id)
                .orElseThrow(() -> new com.pgvpt.exception.PatrimoineNotFoundException("Patrimoine not found with id: " + id));
        return patrimoineMapper.toDto(entity);
    }

    @Override
    public void deletePatrimoine(UUID id) {
        if (!patrimoineRepository.existsById(id)) {
            throw new com.pgvpt.exception.PatrimoineNotFoundException("Patrimoine not found with id: " + id);
        }
        patrimoineRepository.deleteById(id);
    }

    @Override
    public Patrimoine updatePatrimoine(UUID id, PatrimoineUpdate patrimoineUpdate) {
        PatrimoineEntity existing = patrimoineRepository.findById(id)
                .orElseThrow(() -> new com.pgvpt.exception.PatrimoineNotFoundException("Patrimoine not found with id: " + id));
        
        if (patrimoineUpdate instanceof com.pgvpt.dto.MuseeUpdate mUpdate) {
            if (mUpdate.getNom() != null) existing.setNom(mUpdate.getNom());
            if (mUpdate.getDescription() != null) existing.setDescription(mUpdate.getDescription());
        } else if (patrimoineUpdate instanceof com.pgvpt.dto.MonumentUpdate mUpdate) {
            if (mUpdate.getNom() != null) existing.setNom(mUpdate.getNom());
            if (mUpdate.getDescription() != null) existing.setDescription(mUpdate.getDescription());
        } else if (patrimoineUpdate instanceof com.pgvpt.dto.SiteNaturelUpdate sUpdate) {
            if (sUpdate.getNom() != null) existing.setNom(sUpdate.getNom());
            if (sUpdate.getDescription() != null) existing.setDescription(sUpdate.getDescription());
        }
        
        PatrimoineEntity updated = patrimoineRepository.save(existing);
        return patrimoineMapper.toDto(updated);
    }

    @Override
    public PagePatrimoine getPatrimoines(Integer page, Integer size) {
        Page<PatrimoineEntity> entityPage = patrimoineRepository.findAll(
                PageRequest.of(page != null ? page : 0, size != null ? size : 20));
        
        PagePatrimoine result = new PagePatrimoine();
        result.setContent(entityPage.getContent().stream().map(patrimoineMapper::toDto).collect(Collectors.toList()));
        result.setPage(entityPage.getNumber());
        result.setSize(entityPage.getSize());
        result.setTotalElements(entityPage.getTotalElements());
        result.setTotalPages(entityPage.getTotalPages());
        result.setFirst(entityPage.isFirst());
        result.setLast(entityPage.isLast());
        
        return result;
    }
}
