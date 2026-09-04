package com.pgvpt.service.impl;

import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.PointAccesEntity;
import com.pgvpt.repository.PointAccesRepository;
import com.pgvpt.service.PointAccesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PointAccesServiceImpl implements PointAccesService {

    private final PointAccesRepository pointAccesRepository;

    @Override
    public PointAccesEntity createPointAcces(UUID patrimoineId, PointAccesEntity pointAcces) {
        pointAcces.setPatrimoineId(patrimoineId);
        return pointAccesRepository.save(pointAcces);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointAccesEntity> getPointsAccesByPatrimoineId(UUID patrimoineId) {
        return pointAccesRepository.findByPatrimoineId(patrimoineId);
    }

    @Override
    public PointAccesEntity updatePointAcces(UUID id, PointAccesEntity pointAccesUpdate) {
        PointAccesEntity existing = pointAccesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Point d'accès non trouvé avec l'ID: " + id));

        // Application des champs modifiables (patch partiel)
        if (pointAccesUpdate.getNom() != null) existing.setNom(pointAccesUpdate.getNom());
        if (pointAccesUpdate.getType() != null) existing.setType(pointAccesUpdate.getType());
        if (pointAccesUpdate.getDescription() != null) existing.setDescription(pointAccesUpdate.getDescription());
        if (pointAccesUpdate.getLatitude() != null) existing.setLatitude(pointAccesUpdate.getLatitude());
        if (pointAccesUpdate.getLongitude() != null) existing.setLongitude(pointAccesUpdate.getLongitude());
        if (pointAccesUpdate.getAdresse() != null) existing.setAdresse(pointAccesUpdate.getAdresse());
        if (pointAccesUpdate.getAccessiblePMR() != null) existing.setAccessiblePMR(pointAccesUpdate.getAccessiblePMR());
        if (pointAccesUpdate.getParkingDisponible() != null) existing.setParkingDisponible(pointAccesUpdate.getParkingDisponible());
        if (pointAccesUpdate.getTransportPublic() != null) existing.setTransportPublic(pointAccesUpdate.getTransportPublic());
        if (pointAccesUpdate.getDistancePatrimoineMetres() != null) existing.setDistancePatrimoineMetres(pointAccesUpdate.getDistancePatrimoineMetres());
        if (pointAccesUpdate.getHoraires() != null) existing.setHoraires(pointAccesUpdate.getHoraires());

        return pointAccesRepository.save(existing);
    }

    @Override
    public void deletePointAcces(UUID id) {
        if (!pointAccesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Point d'accès non trouvé avec l'ID: " + id);
        }
        pointAccesRepository.deleteById(id);
    }
}
