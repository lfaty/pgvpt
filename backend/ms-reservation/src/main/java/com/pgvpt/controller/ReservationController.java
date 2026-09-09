package com.pgvpt.controller;

import com.pgvpt.api.ReservationsApi;
import com.pgvpt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReservationController implements ReservationsApi {

    @Override
    public ResponseEntity<Participant> addParticipant(UUID id, ParticipantCreate participantCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Reservation> cancelReservation(UUID id, AnnulationRequest annulationRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Reservation> completeReservation(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Reservation> confirmReservation(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Reservation> createReservation(ReservationCreate reservationCreate, String idempotencyKey) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteParticipant(UUID participantId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteReservation(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Reservation> getReservation(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Participant>> getReservationParticipants(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<ReservationPage> searchReservations(Integer page, Integer size, UUID utilisateurId, StatutReservation statut, TypeRessource typeRessource, UUID ressourceId, OffsetDateTime dateDebut, OffsetDateTime dateFin) {
        return null;
    }

    @Override
    public ResponseEntity<Participant> updateParticipant(UUID participantId, ParticipantUpdate participantUpdate) {
        return null;
    }

    @Override
    public ResponseEntity<Reservation> updateReservation(UUID id, ReservationUpdate reservationUpdate) {
        return null;
    }
}
