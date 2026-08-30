package com.pgvpt.service.impl;

import com.pgvpt.dto.Photo;
import com.pgvpt.dto.PhotoCreate;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.mapper.PatrimoineMapper;
import com.pgvpt.model.PatrimoineEntity;
import com.pgvpt.model.PhotoEntity;
import com.pgvpt.repository.PatrimoineRepository;
import com.pgvpt.repository.PhotoRepository;
import com.pgvpt.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final PatrimoineRepository patrimoineRepository;
    private final PhotoRepository photoRepository;

    @Override
    public Photo addPhoto(UUID id, PhotoCreate photoCreate) {
        PatrimoineEntity patrimoine = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));

        PhotoEntity photoEntity = new PhotoEntity();
        if (photoCreate.getUrl() != null) {
            photoEntity.setUrl(photoCreate.getUrl().toString());
        }
        if (photoCreate.getMiniatureUrl() != null) {
            photoEntity.setMiniatureUrl(photoCreate.getMiniatureUrl().toString());
        }
        photoEntity.setTitre(photoCreate.getTitre());
        photoEntity.setDescription(photoCreate.getDescription());
        photoEntity.setAuteur(photoCreate.getAuteur());
        photoEntity.setDatePrise(photoCreate.getDatePrise());
        photoEntity.setDroits(photoCreate.getDroits());
        photoEntity.setLicence(photoCreate.getLicence());
        photoEntity.setPhotoPrincipale(photoCreate.getPhotoPrincipale());
        photoEntity.setOrdre(photoCreate.getOrdre());
        photoEntity.setPatrimoine(patrimoine);

        PhotoEntity saved = photoRepository.save(photoEntity);
        return mapToPhoto(saved);
    }

    @Override
    public void deletePhoto(UUID id, UUID photoId) {
        PatrimoineEntity patrimoine = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));

        PhotoEntity photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException("Photo not found with id: " + photoId));

        if (!photo.getPatrimoine().getId().equals(patrimoine.getId())) {
            throw new IllegalArgumentException("Photo does not belong to the specified Patrimoine");
        }

        photoRepository.delete(photo);
    }

    @Override
    public List<Photo> getPhotos(UUID id) {
        PatrimoineEntity patrimoine = patrimoineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrimoine not found with id: " + id));
        return patrimoine.getPhotos().stream()
                .map(this::mapToPhoto)
                .collect(Collectors.toList());
    }

    private Photo mapToPhoto(PhotoEntity entity) {
        Photo photo = new Photo();
        photo.setId(entity.getId());
        try {
            if (entity.getUrl() != null) {
                photo.setUrl(new java.net.URI(entity.getUrl()));
            }
            if (entity.getMiniatureUrl() != null) {
                photo.setMiniatureUrl(new java.net.URI(entity.getMiniatureUrl()));
            }
        } catch (Exception e) {
            // Ignore invalid URI
        }
        photo.setTitre(entity.getTitre());
        photo.setDescription(entity.getDescription());
        photo.setAuteur(entity.getAuteur());
        photo.setDatePrise(entity.getDatePrise());
        photo.setDroits(entity.getDroits());
        photo.setLicence(entity.getLicence());
        photo.setPhotoPrincipale(entity.getPhotoPrincipale());
        photo.setOrdre(entity.getOrdre());
        return photo;
    }
}
