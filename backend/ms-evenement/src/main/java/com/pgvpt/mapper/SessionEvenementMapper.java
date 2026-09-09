package com.pgvpt.mapper;

import com.pgvpt.dto.SessionEvenement;
import com.pgvpt.dto.SessionEvenementCreate;
import com.pgvpt.dto.SessionEvenementUpdate;
import com.pgvpt.model.SessionEvenementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SessionEvenementMapper {

    SessionEvenement toModel(SessionEvenementEntity entity);

    SessionEvenementEntity toEntity(SessionEvenementCreate request);

    void updateEntity(SessionEvenementUpdate request, @MappingTarget SessionEvenementEntity entity);
}
