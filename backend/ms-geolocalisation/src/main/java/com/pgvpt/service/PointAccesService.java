package com.pgvpt.service;

import com.pgvpt.model.PointAccesEntity;

import java.util.List;
import java.util.UUID;

public interface PointAccesService {

    PointAccesEntity createPointAcces(UUID patrimoineId, PointAccesEntity pointAcces);

    List<PointAccesEntity> getPointsAccesByPatrimoineId(UUID patrimoineId);

    PointAccesEntity updatePointAcces(UUID id, PointAccesEntity pointAccesUpdate);

    void deletePointAcces(UUID id);
}
