package com.pgvpt.controller;

import com.pgvpt.api.ReservationsApi;
import com.pgvpt.dto.*;
import com.pgvpt.mapper.ReservationApiMapper;
import com.pgvpt.model.ReservationEntity;
import com.pgvpt.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReservationController implements ReservationsApi {

    private final ReservationService service;
    private final ReservationApiMapper mapper;

    @Override
    public ResponseEntity<Participant> addParticipant(UUID id, ParticipantCreate participantCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(service.addParticipant(id, participantCreate)));
    }

    @Override
    public ResponseEntity<Reservation> cancelReservation(UUID id, AnnulationRequest annulationRequest) {
        return ResponseEntity.ok(mapper.toApi(service.cancelReservation(id, annulationRequest)));
    }

    @Override
    public ResponseEntity<Reservation> completeReservation(UUID id) {
        return ResponseEntity.ok(mapper.toApi(service.completeReservation(id)));
    }

    @Override
    public ResponseEntity<Reservation> confirmReservation(UUID id) {
        return ResponseEntity.ok(mapper.toApi(service.confirmReservation(id)));
    }

    @Override
    public ResponseEntity<Reservation> createReservation(ReservationCreate reservationCreate, String idempotencyKey) {
        ReservationEntity entity = mapper.toEntity(reservationCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(service.createReservation(entity, idempotencyKey)));
    }

    @Override
    public ResponseEntity<Void> deleteParticipant(UUID participantId) {
        service.deleteParticipant(participantId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteReservation(UUID id) {
        service.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Reservation> getReservation(UUID id) {
        return ResponseEntity.ok(mapper.toApi(service.getReservation(id)));
    }

    @Override
    public ResponseEntity<List<Participant>> getReservationParticipants(UUID id) {
        return ResponseEntity.ok(mapper.toParticipantApi(service.getParticipants(id)));
    }

    @Override
    public ResponseEntity<ReservationPage> searchReservations(Integer page, Integer size, UUID utilisateurId, StatutReservation statut, TypeRessource typeRessource, UUID ressourceId, OffsetDateTime dateDebut, OffsetDateTime dateFin) {
        com.pgvpt.enums.StatutReservation s = statut != null ? com.pgvpt.enums.StatutReservation.valueOf(statut.name()) : null;
        com.pgvpt.enums.TypeRessource t = typeRessource != null ? com.pgvpt.enums.TypeRessource.valueOf(typeRessource.name()) : null;

        Page<ReservationEntity> result = service.searchReservations(utilisateurId, s, t, ressourceId,
                dateDebut != null ? dateDebut.toInstant() : null,
                dateFin != null ? dateFin.toInstant() : null,
                PageRequest.of(page, size));

        ReservationPage response = new ReservationPage();
        response.setContent(mapper.toApi(result.getContent()));
        response.setPage(result.getNumber());
        response.setSize(result.getSize());
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(result.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Participant> updateParticipant(UUID participantId, ParticipantUpdate participantUpdate) {
        return ResponseEntity.ok(mapper.toApi(service.updateParticipant(participantId, participantUpdate)));
    }

    @Override
    public ResponseEntity<Reservation> updateReservation(UUID id, ReservationUpdate reservationUpdate) {
        return ResponseEntity.ok(mapper.toApi(service.updateReservation(id, reservationUpdate)));
    }
}
