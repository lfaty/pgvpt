package com.pgvpt.media.service;

import com.pgvpt.dto.Media;
import com.pgvpt.dto.MediaCreate;
import com.pgvpt.dto.TypeMedia;

import java.util.List;
import java.util.UUID;

public interface MediaService {
    List<Media> findByPatrimoineIdAndType(UUID patrimoineId, TypeMedia type);
    Media getById(UUID id);
    Media create(MediaCreate dto);
    Media update(UUID id, MediaCreate dto) ;
    void delete(UUID id);

}
