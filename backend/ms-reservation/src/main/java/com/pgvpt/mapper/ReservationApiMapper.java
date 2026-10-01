package com.pgvpt.mapper;

import com.pgvpt.dto.Participant;
import com.pgvpt.dto.ParticipantCreate;
import com.pgvpt.dto.Reservation;
import com.pgvpt.dto.ReservationCreate;
import com.pgvpt.model.ParticipantEntity;
import com.pgvpt.model.ReservationEntity;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationApiMapper {

    Reservation toApi(ReservationEntity entity);

    List<Reservation> toApi(List<ReservationEntity> entities);

    ReservationEntity toEntity(Reservation dto);

    ReservationEntity toEntity(ReservationCreate dto);

    Participant toApi(ParticipantEntity entity);

    List<Participant> toParticipantApi(List<ParticipantEntity> entities);

    ParticipantEntity toEntity(ParticipantCreate dto);

    default OffsetDateTime map(Instant instant) {
        return instant == null ? null : instant.atOffset(ZoneOffset.UTC);
    }

    default Instant map(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toInstant();
    }
}
