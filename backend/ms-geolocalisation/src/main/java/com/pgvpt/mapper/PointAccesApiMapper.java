package com.pgvpt.mapper;

import com.dev.pgvpt.api.model.PointAcces;
import com.dev.pgvpt.api.model.PointAccesCreate;
import com.pgvpt.model.PointAccesEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PointAccesApiMapper {

    @Mapping(target = "type", source = "type")
    PointAcces toApi(PointAccesEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patrimoineId", ignore = true)
    @Mapping(target = "distancePatrimoineMetres", ignore = true)
    @Mapping(target = "horaires", ignore = true)
    PointAccesEntity toEntity(PointAccesCreate dto);

    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return value.atOffset(ZoneOffset.UTC);
    }
}
