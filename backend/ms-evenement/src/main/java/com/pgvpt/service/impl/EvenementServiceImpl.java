package com.pgvpt.service.impl;

import com.pgvpt.enums.StatutEvenement;
import com.pgvpt.enums.TypeEvenement;
import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.EvenementEntity;
import com.pgvpt.model.SessionEvenementEntity;
import com.pgvpt.repository.EvenementRepository;
import com.pgvpt.repository.EvenementSpecification;
import com.pgvpt.repository.SessionEvenementRepository;
import com.pgvpt.service.EvenementService;
import com.pgvpt.client.ZoneTouristiqueClient;
import com.pgvpt.client.PatrimoineClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EvenementServiceImpl implements EvenementService {

    private final EvenementRepository evenementRepository;
    private final SessionEvenementRepository sessionRepository;
    private final ZoneTouristiqueClient zoneTouristiqueClient;
    private final PatrimoineClient patrimoineClient;


    @Override
    public EvenementEntity create(EvenementEntity request) {

        if (evenementRepository.existsByTitreIgnoreCase(request.getTitre())) {
            throw new BusinessException("Un événement portant ce titre existe déjà.");
        }
        
        verifierReferences(request.getZoneTouristiqueId(), request.getPatrimoineId());

        request.setStatut(StatutEvenement.BROUILLON);

        return evenementRepository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public EvenementEntity getById(UUID id) {

        return evenementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Événement introuvable : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvenementEntity> search(UUID zoneTouristiqueId, TypeEvenement type, StatutEvenement statut, Instant dateDebut, Instant dateFin, String search, Pageable pageable) {

        Specification<EvenementEntity> spec = EvenementSpecification.search(
                zoneTouristiqueId, type, statut, dateDebut, dateFin, search);

        return evenementRepository.findAll(spec, pageable);
    }

    @Override
    public EvenementEntity update(UUID id, com.pgvpt.dto.EvenementUpdate request) {

        EvenementEntity entity = getById(id);

        if (entity.getStatut() == StatutEvenement.ANNULE) {
            throw new BusinessException("Un événement annulé ne peut plus être modifié.");
        }

        if (entity.getStatut() == StatutEvenement.TERMINE) {
            throw new BusinessException("Un événement terminé ne peut plus être modifié.");
        }

        // Vérifier les références uniquement si elles changent
        UUID newZoneId = request.getZoneTouristiqueId() != null ? request.getZoneTouristiqueId() : entity.getZoneTouristiqueId();
        UUID newPatrimoineId = request.getPatrimoineId() != null ? request.getPatrimoineId() : entity.getPatrimoineId();
        verifierReferences(newZoneId, newPatrimoineId);

        // Mise à jour partielle — seuls les champs non-null sont appliqués
        if (request.getTitre() != null) {
            entity.setTitre(request.getTitre());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getType() != null) {
            entity.setType(com.pgvpt.enums.TypeEvenement.valueOf(request.getType().name()));
        }
        if (request.getZoneTouristiqueId() != null) {
            entity.setZoneTouristiqueId(request.getZoneTouristiqueId());
        }
        if (request.getPatrimoineId() != null) {
            entity.setPatrimoineId(request.getPatrimoineId());
        }
        if (request.getOrganisateurActeurId() != null) {
            entity.setOrganisateurActeurId(request.getOrganisateurActeurId());
        }
        if (request.getCapaciteMax() != null) {
            entity.setCapaciteMax(request.getCapaciteMax());
        }
        if (request.getTarif() != null) {
            entity.setTarif(request.getTarif());
        }
        if (request.getDevise() != null) {
            entity.setDevise(request.getDevise());
        }
        if (request.getReservationRequise() != null) {
            entity.setReservationRequise(request.getReservationRequise());
        }
        if (request.getLangues() != null) {
            entity.setLangues(new java.util.HashSet<>(request.getLangues()));
        }

        return evenementRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {

        EvenementEntity entity = getById(id);

        if (entity.getStatut() == StatutEvenement.OUVERT) {
            throw new BusinessException("Un événement ouvert ne peut pas être supprimé. Annulez-le d'abord.");
        }

        if (entity.getStatut() == StatutEvenement.PROGRAMME) {
            throw new BusinessException("Un événement programmé ne peut pas être supprimé. Annulez-le d'abord.");
        }

        // Supprimer les sessions associées avant l'événement
        sessionRepository.deleteByEvenementId(id);

        evenementRepository.delete(entity);
    }

    @Override
    public EvenementEntity programmer(UUID id) {

        EvenementEntity evenement = getById(id);

        if (evenement.getStatut() != StatutEvenement.BROUILLON) {
            throw new BusinessException("Seul un événement en brouillon peut être programmé.");
        }

        verifierSessions(id);

        evenement.setStatut(StatutEvenement.PROGRAMME);

        return evenementRepository.save(evenement);
    }

    @Override
    public EvenementEntity ouvrir(UUID id) {

        EvenementEntity evenement = getById(id);

        if (evenement.getStatut() != StatutEvenement.PROGRAMME) {
            throw new BusinessException("Seul un événement programmé peut être ouvert.");
        }

        verifierSessions(id);

        evenement.setStatut(StatutEvenement.OUVERT);

        return evenementRepository.save(evenement);
    }

    @Override
    public EvenementEntity annuler(UUID id) {

        EvenementEntity evenement = getById(id);

        if (evenement.getStatut() == StatutEvenement.TERMINE) {
            throw new BusinessException("Un événement terminé ne peut pas être annulé.");
        }

        if (evenement.getStatut() == StatutEvenement.ANNULE) {
            throw new BusinessException("L'événement est déjà annulé.");
        }

        evenement.setStatut(StatutEvenement.ANNULE);

        return evenementRepository.save(evenement);
    }

    @Override
    public EvenementEntity terminer(UUID id) {

        EvenementEntity evenement = getById(id);

        if (evenement.getStatut() != StatutEvenement.OUVERT) {
            throw new BusinessException("Seul un événement ouvert peut être terminé.");
        }

        evenement.setStatut(StatutEvenement.TERMINE);

        return evenementRepository.save(evenement);
    }

    private void verifierSessions(UUID evenementId) {

        List<SessionEvenementEntity> sessions = sessionRepository.findByEvenementIdOrderByDateDebutAsc(evenementId);

        if (sessions.isEmpty()) {
            throw new BusinessException("L'événement doit posséder au moins une session.");
        }

        sessions.forEach(session -> {

            if (!session.getDateFin().isAfter(session.getDateDebut())) {
                throw new BusinessException("La date de fin doit être postérieure à la date de début.");
            }
        });
    }

    private void verifierReferences(UUID zoneTouristiqueId, UUID patrimoineId) {
        if (zoneTouristiqueId != null) {
            try {
                zoneTouristiqueClient.getById(zoneTouristiqueId);
            } catch (FeignException.NotFound e) {
                throw new BusinessException("La zone touristique specifiee (" + zoneTouristiqueId + ") n'existe pas.");
            } catch (Exception e) {
                // Service indisponible → on tolère (fail-open)
                log.warn("Impossible de vérifier la zone touristique {} : {}", zoneTouristiqueId, e.getMessage());
            }
        }
        
        if (patrimoineId != null) {
            try {
                patrimoineClient.getById(patrimoineId);
            } catch (FeignException.NotFound e) {
                throw new BusinessException("Le patrimoine specifie (" + patrimoineId + ") n'existe pas.");
            } catch (Exception e) {
                log.warn("Impossible de vérifier le patrimoine {} : {}", patrimoineId, e.getMessage());
            }
        }
    }
}
