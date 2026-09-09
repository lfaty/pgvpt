package com.pgvpt.mapper;

import com.pgvpt.dto.Evenement;
import com.pgvpt.dto.EvenementCreate;
import com.pgvpt.dto.EvenementUpdate;
import com.pgvpt.model.EvenementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EvenementMapper {

    Evenement toApi(EvenementEntity entity);

    EvenementEntity toEntity(EvenementCreate request);

    void updateEntity(EvenementUpdate request, @MappingTarget EvenementEntity entity);
}