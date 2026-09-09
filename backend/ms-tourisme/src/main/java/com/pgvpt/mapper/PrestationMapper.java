package com.pgvpt.mapper;

import com.pgvpt.dto.Prestation;
import com.pgvpt.dto.PrestationCreate;
import com.pgvpt.dto.PrestationUpdate;
import com.pgvpt.model.PrestationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PrestationMapper {

    Prestation toApi(PrestationEntity entity);

    PrestationEntity toEntity(PrestationCreate request);

    void updateEntity(PrestationUpdate request, @MappingTarget PrestationEntity entity);
}
