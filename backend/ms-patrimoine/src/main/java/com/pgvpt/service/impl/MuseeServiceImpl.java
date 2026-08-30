package com.pgvpt.service.impl;

import com.pgvpt.dto.Musee;
import com.pgvpt.dto.MuseeCreate;
import com.pgvpt.dto.MuseeUpdate;
import com.pgvpt.exception.InvalidRequestException;
import com.pgvpt.exception.ResourceNotFoundException;

import com.pgvpt.mapper.PatrimoineMapper;
import com.pgvpt.model.MuseeEntity;
import com.pgvpt.repository.PatrimoineRepository;
import com.pgvpt.service.MuseeService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MuseeServiceImpl implements MuseeService {

    private final PatrimoineRepository patrimoineRepository;

    private final PatrimoineMapper patrimoineMapper;

    // =========================================================
    // CREATE
    // =========================================================

    @Override
    public Musee create(MuseeCreate request) {

        if (request == null) {
            throw new InvalidRequestException(
                    "Les données du musée sont obligatoires"
            );
        }

        MuseeEntity entity =
                patrimoineMapper.toMuseeEntity(request);

        MuseeEntity saved =
                patrimoineRepository.save(entity);

        return patrimoineMapper.toMuseeDto(saved);
    }

    // =========================================================
    // FIND BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Musee findById(UUID id) {

        validateId(id);

        MuseeEntity entity =
                patrimoineRepository.findById(id)
                        .filter(MuseeEntity.class::isInstance)
                        .map(MuseeEntity.class::cast)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Musée introuvable : " + id
                                )
                        );

        return patrimoineMapper.toMuseeDto(entity);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Override
    public Musee update(
            UUID id,
            MuseeUpdate request) {

        validateId(id);

        if (request == null) {
            throw new InvalidRequestException(
                    "Les données du musée sont obligatoires"
            );
        }

        MuseeEntity entity =
                patrimoineRepository.findById(id)
                        .filter(MuseeEntity.class::isInstance)
                        .map(MuseeEntity.class::cast)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Musée introuvable : " + id
                                )
                        );

        patrimoineMapper.updateMuseeEntity(
                request,
                entity
        );

        return patrimoineMapper.toMuseeDto(entity);
    }

    private void validateId(UUID id) {

        if (id == null) {
            throw new InvalidRequestException(
                    "L'identifiant du musée est obligatoire"
            );
        }
    }
}