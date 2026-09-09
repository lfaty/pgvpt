package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.ActiviteEntity;
import org.mapstruct.MappingTarget;


public interface ActiviteMapper {

    Activite toApi(ActiviteEntity entity);

    ActiviteEntity toEntity(ActiviteCreateRequest activiteCreateRequest);
    void updateEntity(ActiviteUpdateRequest request, @MappingTarget ActiviteEntity entity);
}
