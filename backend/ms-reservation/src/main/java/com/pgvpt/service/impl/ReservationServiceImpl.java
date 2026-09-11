package com.pgvpt.service.impl;

import com.pgvpt.dto.AnnulationRequest;
import com.pgvpt.dto.DisponibiliteResponse;
import com.pgvpt.dto.ParticipantCreate;
import com.pgvpt.dto.ParticipantUpdate;
import com.pgvpt.dto.ReservationUpdate;
import com.pgvpt.enums.StatutReservation;
import com.pgvpt.enums.TypeRessource;
import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.ParticipantEntity;
import com.pgvpt.model.ReservationEntity;
import com.pgvpt.repository.ParticipantRepository;
import com.pgvpt.repository.ReservationRepository;
import com.pgvpt.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public ReservationEntity createReservation(ReservationEntity reservation, String idempotencyKey) {
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            Optional<ReservationEntity> existing = reservationRepository.findAll().stream()
                    .filter(r -> idempotencyKey.equals(r.getIdempotencyKey()))
                    .findFirst();
            if (existing.isPresent()) {
                return existing.get();
            }
            reservation.setIdempotencyKey(idempotencyKey);
        }

        // Vérification de disponibilité (Mock - à remplacer par appels Feign)
        DisponibiliteResponse dispo = checkAvailability(reservation.getTypeRessource(), reservation.getRessourceId(), reservation.getDateReservation(), reservation.getNombrePersonnes());
        if (!Boolean.TRUE.equals(dispo.getDisponible())) {
            throw new BusinessException("Ressource non disponible pour le nombre de personnes demandé.");
        }

        // Génération d'un numéro unique
        reservation.setNumero("RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        
        // Mock calcul montant
        reservation.setMontantTotal(reservation.getNombrePersonnes() * 15000.0);
        reservation.setStatut(StatutReservation.EN_ATTENTE);

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationEntity> searchReservations(UUID utilisateurId, StatutReservation statut,
                                                      TypeRessource typeRessource, UUID ressourceId,
                                                      Instant dateDebut, Instant dateFin, Pageable pageable) {
        return reservationRepository.rechercher(utilisateurId, statut, typeRessource, ressourceId, dateDebut, dateFin, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationEntity getReservation(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable : " + id));
    }

    @Override
    public ReservationEntity updateReservation(UUID id, ReservationUpdate update) {
        ReservationEntity existing = getReservation(id);

        if (existing.getStatut() != StatutReservation.EN_ATTENTE) {
            throw new BusinessException("Impossible de modifier une réservation qui n'est pas EN_ATTENTE.");
        }

        if (update.getNombrePersonnes() != null) {
            existing.setNombrePersonnes(update.getNombrePersonnes());
            existing.setMontantTotal(existing.getNombrePersonnes() * 15000.0);
        }

        if (update.getCommentaire() != null) {
            existing.setCommentaire(update.getCommentaire());
        }

        if (update.getDateReservation() != null) {
            existing.setDateReservation(update.getDateReservation().toInstant());
        }

        return reservationRepository.save(existing);
    }

    @Override
    public void deleteReservation(UUID id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Réservation introuvable : " + id);
        }
        participantRepository.deleteByReservationId(id);
        reservationRepository.deleteById(id);
    }

    @Override
    public ReservationEntity confirmReservation(UUID id) {
        ReservationEntity existing = getReservation(id);
        if (existing.getStatut() != StatutReservation.EN_ATTENTE) {
            throw new BusinessException("Seule une réservation EN_ATTENTE peut être confirmée.");
        }
        existing.setStatut(StatutReservation.CONFIRMEE);
        existing.setDateConfirmation(Instant.now());
        return reservationRepository.save(existing);
    }

    @Override
    public ReservationEntity cancelReservation(UUID id, AnnulationRequest request) {
        ReservationEntity existing = getReservation(id);
        if (existing.getStatut() == StatutReservation.ANNULEE || existing.getStatut() == StatutReservation.TERMINEE) {
            throw new BusinessException("Cette réservation ne peut plus être annulée.");
        }
        existing.setStatut(StatutReservation.ANNULEE);
        existing.setDateAnnulation(Instant.now());
        if (request != null && request.getMotif() != null) {
            existing.setMotifAnnulation(request.getMotif());
        }
        return reservationRepository.save(existing);
    }

    @Override
    public ReservationEntity completeReservation(UUID id) {
        ReservationEntity existing = getReservation(id);
        if (existing.getStatut() != StatutReservation.CONFIRMEE) {
            throw new BusinessException("Seule une réservation CONFIRMEE peut être terminée.");
        }
        existing.setStatut(StatutReservation.TERMINEE);
        return reservationRepository.save(existing);
    }

    @Override
    public ParticipantEntity addParticipant(UUID reservationId, ParticipantCreate participant) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable : " + reservationId));
        ParticipantEntity entity = new ParticipantEntity();
        entity.setReservation(reservation);
        entity.setNom(participant.getNom());
        entity.setPrenom(participant.getPrenom());
        entity.setEmail(participant.getEmail());
        entity.setTelephone(participant.getTelephone());
        entity.setAge(participant.getAge());
        return participantRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipantEntity> getParticipants(UUID reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new ResourceNotFoundException("Réservation introuvable : " + reservationId);
        }
        return participantRepository.findByReservationIdOrderByNomAscPrenomAsc(reservationId);
    }

    @Override
    public ParticipantEntity updateParticipant(UUID participantId, ParticipantUpdate update) {
        ParticipantEntity existing = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant introuvable : " + participantId));
        if (update.getNom() != null) existing.setNom(update.getNom());
        if (update.getPrenom() != null) existing.setPrenom(update.getPrenom());
        if (update.getEmail() != null) existing.setEmail(update.getEmail());
        if (update.getTelephone() != null) existing.setTelephone(update.getTelephone());
        if (update.getAge() != null) existing.setAge(update.getAge());
        return participantRepository.save(existing);
    }

    @Override
    public void deleteParticipant(UUID participantId) {
        if (!participantRepository.existsById(participantId)) {
            throw new ResourceNotFoundException("Participant introuvable : " + participantId);
        }
        participantRepository.deleteById(participantId);
    }

    @Override
    @Transactional(readOnly = true)
    public DisponibiliteResponse checkAvailability(TypeRessource typeRessource, UUID ressourceId, Instant date, int nombrePersonnes) {
        // Mock de disponibilité
        DisponibiliteResponse response = new DisponibiliteResponse();
        response.setDisponible(true);
        response.setCapaciteDisponible(50);
        response.setCapaciteMax(100);
        response.setMessage("Disponibilité vérifiée (Mock).");
        return response;
    }
}
