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

    default java.time.OffsetDateTime map(java.time.Instant value) {
        return value != null ? java.time.OffsetDateTime.ofInstant(value, java.time.ZoneOffset.UTC) : null;
    }

    default java.time.Instant map(java.time.OffsetDateTime value) {
        return value != null ? value.toInstant() : null;
    }
}
