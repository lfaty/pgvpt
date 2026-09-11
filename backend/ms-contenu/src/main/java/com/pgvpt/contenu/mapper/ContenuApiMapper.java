package com.pgvpt.contenu.mapper;

import com.pgvpt.dto.Contenu;
import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.dto.ContenuCreate;
import com.pgvpt.dto.ContenuUpdate;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ContenuApiMapper {

    Contenu toApi(ContenuEntity entity);

    List<Contenu> toApi(List<ContenuEntity> entity);

    ContenuEntity toEntity(Contenu dto);

    ContenuEntity toEntity(ContenuCreate dto);

    ContenuEntity toEntity(ContenuUpdate dto);

    com.pgvpt.contenu.enums.Statut toEntityEnum(com.pgvpt.dto.StatutContenu statut);

    // --- Conversions de types ---

    default OffsetDateTime map(Instant instant) {
        return instant == null ? null : instant.atOffset(ZoneOffset.UTC);
    }

    default Instant map(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toInstant();
    }

    default String map(List<String> list) {
        return list == null || list.isEmpty() ? null : String.join(",", list);
    }

    default List<String> mapToList(String value) {
        return value == null || value.isBlank() ? Collections.emptyList()
                : Arrays.asList(value.split(","));
    }
}

