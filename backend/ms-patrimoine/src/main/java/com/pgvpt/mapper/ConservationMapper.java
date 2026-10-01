package com.pgvpt.mapper;

import com.pgvpt.dto.Conservation;
import com.pgvpt.model.ConservationEntity;
import org.mapstruct.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConservationMapper {

    ConservationEntity toEntity(Conservation dto);
    Conservation toDto(ConservationEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(Conservation dto, @MappingTarget ConservationEntity entity);

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
