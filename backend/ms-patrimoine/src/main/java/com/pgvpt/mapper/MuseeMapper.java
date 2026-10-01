package com.pgvpt.mapper;

import com.pgvpt.dto.Musee;
import com.pgvpt.dto.MuseeCreate;
import com.pgvpt.dto.MuseeUpdate;
import com.pgvpt.model.MuseeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MuseeMapper {

    MuseeEntity toEntity(MuseeCreate dto);

    MuseeEntity toEntity(MuseeUpdate dto);

    Musee toDto(MuseeEntity entity);

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
