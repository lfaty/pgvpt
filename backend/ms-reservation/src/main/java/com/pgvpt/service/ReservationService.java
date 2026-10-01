package com.pgvpt.service;

import com.pgvpt.dto.AnnulationRequest;
import com.pgvpt.dto.DisponibiliteResponse;
import com.pgvpt.dto.ParticipantCreate;
import com.pgvpt.dto.ParticipantUpdate;
import com.pgvpt.dto.ReservationUpdate;
import com.pgvpt.enums.StatutReservation;
import com.pgvpt.enums.TypeRessource;
import com.pgvpt.model.ParticipantEntity;
import com.pgvpt.model.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ReservationService {

    ReservationEntity createReservation(ReservationEntity reservation, String idempotencyKey);

    Page<ReservationEntity> searchReservations(UUID utilisateurId, StatutReservation statut,
                                               TypeRessource typeRessource, UUID ressourceId,
                                               Instant dateDebut, Instant dateFin, Pageable pageable);

    ReservationEntity getReservation(UUID id);

    ReservationEntity updateReservation(UUID id, ReservationUpdate update);

    void deleteReservation(UUID id);

    ReservationEntity confirmReservation(UUID id);

    ReservationEntity cancelReservation(UUID id, AnnulationRequest request);

    ReservationEntity completeReservation(UUID id);

    ParticipantEntity addParticipant(UUID reservationId, ParticipantCreate participant);

    List<ParticipantEntity> getParticipants(UUID reservationId);

    ParticipantEntity updateParticipant(UUID participantId, ParticipantUpdate update);

    void deleteParticipant(UUID participantId);

    DisponibiliteResponse checkAvailability(TypeRessource typeRessource, UUID ressourceId, Instant date, int nombrePersonnes);
}
