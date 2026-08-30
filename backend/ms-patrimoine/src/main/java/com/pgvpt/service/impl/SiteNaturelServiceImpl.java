package com.pgvpt.service.impl;

import com.pgvpt.dto.SiteNaturel;
import com.pgvpt.dto.SiteNaturelCreate;
import com.pgvpt.dto.SiteNaturelUpdate;
import com.pgvpt.exception.InvalidRequestException;
import com.pgvpt.exception.ResourceNotFoundException;

import com.pgvpt.mapper.PatrimoineMapper;
import com.pgvpt.model.SiteNaturelEntity;
import com.pgvpt.repository.PatrimoineRepository;
import com.pgvpt.service.SiteNaturelService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SiteNaturelServiceImpl implements SiteNaturelService {

    private final PatrimoineRepository patrimoineRepository;

    private final PatrimoineMapper patrimoineMapper;

    // =========================================================
    // CREATE
    // =========================================================

    @Override
    public SiteNaturel create(
            SiteNaturelCreate request) {

        if (request == null) {
            throw new InvalidRequestException(
                    "Les données du site naturel sont obligatoires"
            );
        }

        SiteNaturelEntity entity =
                patrimoineMapper.toSiteNaturelEntity(request);

        SiteNaturelEntity saved =
                patrimoineRepository.save(entity);

        return patrimoineMapper.toSiteNaturelDto(saved);
    }

    // =========================================================
    // FIND BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public SiteNaturel findById(UUID id) {

        validateId(id);

        SiteNaturelEntity entity =
                patrimoineRepository.findById(id)
                        .filter(SiteNaturelEntity.class::isInstance)
                        .map(SiteNaturelEntity.class::cast)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Site naturel introuvable : "
                                                + id
                                )
                        );

        return patrimoineMapper.toSiteNaturelDto(entity);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Override
    public SiteNaturel update(
            UUID id,
            SiteNaturelUpdate request) {

        validateId(id);

        if (request == null) {
            throw new InvalidRequestException(
                    "Les données du site naturel sont obligatoires"
            );
        }

        SiteNaturelEntity entity =
                patrimoineRepository.findById(id)
                        .filter(SiteNaturelEntity.class::isInstance)
                        .map(SiteNaturelEntity.class::cast)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Site naturel introuvable : "
                                                + id
                                )
                        );

        patrimoineMapper.updateSiteNaturelEntity(
                request,
                entity
        );

        return patrimoineMapper.toSiteNaturelDto(entity);
    }

    private void validateId(UUID id) {

        if (id == null) {
            throw new InvalidRequestException(
                    "L'identifiant du site naturel est obligatoire"
            );
        }
    }
}