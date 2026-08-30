package com.pgvpt.service.impl;

import com.pgvpt.dto.Monument;
import com.pgvpt.dto.MonumentCreate;
import com.pgvpt.dto.MonumentUpdate;
import com.pgvpt.exception.BusinessRuleException;
import com.pgvpt.exception.InvalidRequestException;
import com.pgvpt.exception.ResourceNotFoundException;

import com.pgvpt.mapper.PatrimoineMapper;
import com.pgvpt.model.MonumentEntity;
import com.pgvpt.repository.PatrimoineRepository;
import com.pgvpt.service.MonumentService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MonumentServiceImpl implements MonumentService {

    private final PatrimoineRepository patrimoineRepository;

    private final PatrimoineMapper patrimoineMapper;

    // =========================================================
    // CREATE
    // =========================================================

    @Override
    public Monument create(MonumentCreate request) {

        if (request == null) {
            throw new InvalidRequestException(
                    "Les données du monument sont obligatoires"
            );
        }

        MonumentEntity entity =
                patrimoineMapper.toMonumentEntity(request);

        MonumentEntity saved =
                patrimoineRepository.save(entity);

        return patrimoineMapper.toMonumentDto(saved);
    }

    // =========================================================
    // FIND BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Monument findById(UUID id) {

        validateId(id);

        MonumentEntity entity =
                patrimoineRepository.findById(id)
                        .filter(MonumentEntity.class::isInstance)
                        .map(MonumentEntity.class::cast)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Monument introuvable : " + id
                                )
                        );

        return patrimoineMapper.toMonumentDto(entity);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Override
    public Monument update(
            UUID id,
            MonumentUpdate request) {

        validateId(id);

        if (request == null) {
            throw new InvalidRequestException(
                    "Les données du monument sont obligatoires"
            );
        }

        MonumentEntity entity =
                patrimoineRepository.findById(id)
                        .filter(MonumentEntity.class::isInstance)
                        .map(MonumentEntity.class::cast)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Monument introuvable : " + id
                                )
                        );

        patrimoineMapper.updateMonumentEntity(
                request,
                entity
        );

        return patrimoineMapper.toMonumentDto(entity);
    }

    private void validateId(UUID id) {

        if (id == null) {
            throw new InvalidRequestException(
                    "L'identifiant du monument est obligatoire"
            );
        }
    }
}