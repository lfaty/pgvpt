package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.MonumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MonumentMapper {

    MonumentEntity toEntity(MonumentCreate dto);

    MonumentEntity toEntity(MonumentUpdate dto);

    Monument toDto(MonumentEntity entity);

    default Instant map(OffsetDateTime value) {
        return value != null
                ? value.toInstant()
                : null;
    }

    default OffsetDateTime map(Instant value) {
        return value != null
                ? value.atOffset(ZoneOffset.UTC)
                : null;
    }
}
