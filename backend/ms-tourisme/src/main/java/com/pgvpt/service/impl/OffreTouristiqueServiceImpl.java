package com.pgvpt.service.impl;

import com.pgvpt.enums.CategorieOffre;
import com.pgvpt.enums.StatutOffre;
import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.OffreTouristiqueEntity;
import com.pgvpt.repository.OffreTouristiqueRepository;
import com.pgvpt.repository.OffreTouristiqueSpecification;
import com.pgvpt.repository.PrestationRepository;
import com.pgvpt.service.OffreTouristiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OffreTouristiqueServiceImpl implements OffreTouristiqueService {

    private final OffreTouristiqueRepository repository;
    private final PrestationRepository prestationRepository;

    @Override
    @Transactional(readOnly = true)
    public OffreTouristiqueEntity getById(UUID id) {

        return repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Offre touristique introuvable : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OffreTouristiqueEntity> search(int page, int size, String search, UUID zoneTouristiqueId, UUID acteurId, UUID circuitId, CategorieOffre categorie, StatutOffre statut) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("nom").ascending());

        Specification<OffreTouristiqueEntity> specification = OffreTouristiqueSpecification.search(search, zoneTouristiqueId, acteurId, circuitId, categorie, statut);

        return repository.findAll(specification, pageable);
    }

    @Override
    public OffreTouristiqueEntity create(OffreTouristiqueEntity request) {

        if (repository.existsByNomIgnoreCase(request.getNom())) {
            throw new BusinessException("Une offre portant le nom '" + request.getNom() + "' existe déjà.");
        }

        validerPeriode(request.getDateDebutValidite(), request.getDateFinValidite());

        request.setStatut(StatutOffre.BROUILLON);
        request.setActif(true);

        if (request.getPrix() == null) {
            request.setPrix(BigDecimal.ZERO);
        }

        return repository.save(request);
    }


    @Override
    public OffreTouristiqueEntity update(UUID id, OffreTouristiqueEntity request) {

        OffreTouristiqueEntity entity = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Offre introuvable : " + id));

        if (entity.getStatut() == StatutOffre.ARCHIVEE) {
            throw new BusinessException("Une offre archivée ne peut plus être modifiée.");
        }

        if (repository.existsByNomIgnoreCaseAndIdNot(request.getNom(), id)) {
            throw new BusinessException("Une autre offre porte déjà ce nom.");
        }

        validerPeriode(request.getDateDebutValidite(), request.getDateFinValidite());

        entity.setActeurId(request.getActeurId());
        entity.setActif(request.isActif());
        entity.setPrix(request.getPrix());
        entity.setCategorie(request.getCategorie());
        entity.setStatut(request.getStatut());
        entity.setCapaciteMax(request.getCapaciteMax());
        entity.setDescription(entity.getDescription());
        entity.setDevise(request.getDevise());
        entity.setNom(request.getNom());
        entity.setCircuitId(request.getCircuitId());
        entity.setDureeMinutes(request.getDureeMinutes());
        entity.setZoneTouristiqueId(request.getZoneTouristiqueId());
        entity.setDateDebutValidite(request.getDateDebutValidite());
        entity.setDateFinValidite(request.getDateFinValidite());

        return repository.save(entity);
    }

    @Override
    public void delete(UUID id) {

        OffreTouristiqueEntity entity = getEntity(id);

        if (entity.getStatut() == StatutOffre.PUBLIEE) {
            throw new BusinessException("Une offre publiée ne peut pas être supprimée.");
        }

        prestationRepository.deleteByOffreId(id);

        repository.delete(entity);
    }

    @Override
    public OffreTouristiqueEntity publier(UUID id) {

        OffreTouristiqueEntity entity = getEntity(id);

        if (entity.getStatut() == StatutOffre.ARCHIVEE) {
            throw new BusinessException("Une offre archivée ne peut pas être publiée.");
        }

        if (!entity.isActif()) {
            throw new BusinessException("L'offre doit être active avant publication.");
        }

        entity.setStatut(StatutOffre.PUBLIEE);

        return repository.save(entity);
    }

    @Override
    public OffreTouristiqueEntity depublier(UUID id) {
        return null;
    }

    @Override
    public OffreTouristiqueEntity activer(UUID id) {

        OffreTouristiqueEntity entity = getEntity(id);

        if (entity.getStatut() == StatutOffre.ARCHIVEE) {
            throw new BusinessException("Une offre archivée ne peut pas être activée.");
        }

        entity.setActif(true);

        return repository.save(entity);
    }

    @Override
    public OffreTouristiqueEntity desactiver(UUID id) {

        OffreTouristiqueEntity entity = getEntity(id);

        entity.setActif(false);

        if (entity.getStatut() == StatutOffre.PUBLIEE) {
            entity.setStatut(StatutOffre.DEPUBLIEE);
        }

        return repository.save(entity);
    }

    @Override
    public OffreTouristiqueEntity archiver(UUID id) {
        return null;
    }

    @Override
    public BigDecimal calculerPrix(UUID id) {
        return null;
    }

    private OffreTouristiqueEntity getEntity(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit introuvable avec l'identifiant : " + id));
    }

    private void validerPeriode(LocalDate debut, LocalDate fin) {

        if (debut != null && fin != null && fin.isBefore(debut)) {
            throw new BusinessException("La date de fin de validité doit être postérieure ou égale à la date de début.");
        }
    }

}
