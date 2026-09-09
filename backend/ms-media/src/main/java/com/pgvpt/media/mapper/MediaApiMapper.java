package com.pgvpt.media.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.media.model.MediaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaApiMapper {
    Media toApi(MediaEntity entity);
    List<Media> toApi(List<MediaEntity> entities);
    MediaEntity toEntity(Media dto);
    MediaEntity toEntity(MediaCreate dto);

    com.pgvpt.media.enums.TypeMedia toEntityEnum(com.pgvpt.dto.TypeMedia typeMedia);
}
