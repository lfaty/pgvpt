package com.pgvpt.service.impl;

import com.pgvpt.dto.*;
import com.pgvpt.enums.StatutPatrimoineMetier;
import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.mapper.PatrimoineMapper;
import com.pgvpt.model.ConservationEntity;
import com.pgvpt.model.EspeceProtegeeEntity;
import com.pgvpt.model.PatrimoineEntity;
import com.pgvpt.model.SiteNaturelEntity;
import com.pgvpt.record.*;
import com.pgvpt.repository.PatrimoineRepository;
import com.pgvpt.service.PatrimoineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
@Transactional
public class PatrimoineServiceImpl implements PatrimoineService {

    private final PatrimoineRepository patrimoineRepository;
    private final PatrimoineMapper patrimoineMapper;

    @Override
    @Transactional(readOnly = true)
    public PagePatrimoine getAllPatrimoines(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PatrimoineEntity> entityPage = patrimoineRepository.findAll(pageable);

        return new PagePatrimoine()
                .content(entityPage.getContent().stream().map(patrimoineMapper::toDto).toList())
                .page(entityPage.getNumber())
                .size(entityPage.getSize())
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .first(entityPage.isFirst())
                .last(entityPage.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public PagePatrimoine getPatrimoines(int page, int size, String sort, PatrimoineSearchCriteria criteria) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        Specification<PatrimoineEntity> specification = PatrimoineSpecifications.search(criteria);
        Page<PatrimoineEntity> entityPage = patrimoineRepository.findAll(specification, pageable);

        return new PagePatrimoine()
                .content(entityPage.getContent().stream().map(patrimoineMapper::toDto).toList())
                .page(entityPage.getNumber())
                .size(entityPage.getSize())
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .first(entityPage.isFirst())
                .last(entityPage.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public Patrimoine getById(UUID id) {
        return patrimoineRepository.findById(id).map(patrimoineMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine introuvable"));
    }

    @Override
    public void delete(UUID id) {
        if (!patrimoineRepository.existsById(id)) throw new ResourceNotFoundException("Patrimoine introuvable");
        patrimoineRepository.deleteById(id);
    }

    @Override
    @Transactional // Indispensable pour la persistance des graphes d'entités avec cascade
    public Patrimoine createPatrimoine(PatrimoineCreate dto) {
        // Le mapper gère désormais l'instanciation ET les liens bidirectionnels (SiteNaturel, Musée, Monument)
        PatrimoineEntity patrimoine = patrimoineMapper.toEntity(dto);

        PatrimoineEntity savedPatrimoine = patrimoineRepository.save(patrimoine);

        return patrimoineMapper.toDto(savedPatrimoine);
    }


    @Override
    public Patrimoine updatePatrimoine(UUID id, PatrimoineUpdate dto) {
        PatrimoineEntity entity = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine introuvable"));
        patrimoineMapper.updateEntity(dto, entity);
        return patrimoineMapper.toDto(patrimoineRepository.save(entity));
    }

    @Override
    @Transactional
    public Patrimoine updateStatutPatrimoine(UUID id, PatrimoineStatutUpdate dto) {
        // Récupération de l'entité existante
        PatrimoineEntity patrimoine = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine introuvable avec l'ID : " + id));

        // Conversion et application du nouveau statut métier
        StatutPatrimoineMetier nouveauStatut = patrimoineMapper.toStatut(dto.getStatut());
        patrimoine.setStatut(nouveauStatut);

        // Logique métier additionnelle : gestion de la date de publication
        if (nouveauStatut == StatutPatrimoineMetier.PUBLIE) {
            patrimoine.setPublishedAt(Instant.now());
        }

        // Sauvegarde et conversion vers le DTO de sortie
        PatrimoineEntity patrimoineMisAJour = patrimoineRepository.save(patrimoine);
        return patrimoineMapper.toDto(patrimoineMisAJour);
    }

    @Override
    public Patrimoine depublier(UUID id) {
        PatrimoineEntity entity = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine introuvable"));

        if (entity.getStatut() != StatutPatrimoineMetier.PUBLIE) { throw new BusinessException("Le patrimoine n'est pas publié");}

        entity.setStatut(StatutPatrimoineMetier.VALIDE);
        entity.setPublishedAt(null);

        return patrimoineMapper.toDto(patrimoineRepository.save(entity));
    }

    @Override
    public List<HoraireOuverture> getHoraires(UUID id) {
        PatrimoineEntity entity = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine introuvable"));

        return patrimoineMapper.toHoraireDtoList(entity.getHoraires());
    }

    @Override
    @Transactional
    public List<HoraireOuverture> updateHoraires(UUID id, List<HoraireOuverture> dtos) {
        PatrimoineEntity entity = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine introuvable avec l'ID : " + id));
        if (dtos == null) { dtos = emptyList(); }
        entity.getHoraires().clear();
        if (!dtos.isEmpty()) { entity.getHoraires().addAll(patrimoineMapper.toHoraireEmbeddableList(dtos)); }
        PatrimoineEntity savedEntity = patrimoineRepository.saveAndFlush(entity);

        return patrimoineMapper.toHoraireDtoList(savedEntity.getHoraires());
    }


    @Override
    public Conservation getConservation(UUID id) {
        PatrimoineEntity entity = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine introuvable"));
        return patrimoineMapper.toConservationDto(entity.getConservation());
    }

    @Override
    public Conservation updateConservation(UUID id, Conservation dto) {
        PatrimoineEntity entity = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine introuvable"));
        if (entity.getConservation() == null) {
            entity.setConservation(new ConservationEntity());
        }
        patrimoineMapper.updateConservationEntity(dto, entity.getConservation());
        patrimoineRepository.save(entity);
        return patrimoineMapper.toConservationDto(entity.getConservation());
    }


    // --- MÉTHODES PRIVÉES DE VALIDATION ET UTILITAIRES ---
    private Sort buildSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.ASC, "nom");
        }

        String[] sortParams = sort.split(",");
        String property = sortParams[0].trim();

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1].trim())) {
            direction = Sort.Direction.DESC;
        }
        return Sort.by(direction, property);
    }
}
