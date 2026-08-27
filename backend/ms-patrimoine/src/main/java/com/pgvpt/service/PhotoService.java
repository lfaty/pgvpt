package com.pgvpt.service;

import com.pgvpt.dto.Photo;
import com.pgvpt.dto.PhotoCreate;
import java.util.List;
import java.util.UUID;

public interface PhotoService {
    Photo addPhoto(UUID id, PhotoCreate photoCreate);
    void deletePhoto(UUID id, UUID photoId);
    List<Photo> getPhotos(UUID id);
}
