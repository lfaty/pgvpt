package com.pgvpt.media.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.media.model.MediaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaApiMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    MediaEntity toEntity(MediaCreate dto);

    Media toApi(MediaEntity entity);
    List<Media> toApi(List<MediaEntity> entities);

    com.pgvpt.media.enums.TypeMedia toEntityEnum(com.pgvpt.dto.TypeMedia typeMedia);

    // --- Conversions de types ---

    default OffsetDateTime map(Instant instant) {
        return instant == null ? null : instant.atOffset(ZoneOffset.UTC);
    }

    default Instant map(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toInstant();
    }

    default URI mapStringToUri(String value) {
        return value == null ? null : URI.create(value);
    }

    default String mapUriToString(URI value) {
        return value == null ? null : value.toString();
    }
}
