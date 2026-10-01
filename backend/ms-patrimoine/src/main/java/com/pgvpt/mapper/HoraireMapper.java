package com.pgvpt.mapper;

import com.pgvpt.dto.HoraireOuverture;
import com.pgvpt.embeddable.HoraireOuvertureEmbeddable;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HoraireMapper {

    HoraireOuverture toDto(HoraireOuvertureEmbeddable entity);

    List<HoraireOuverture> toDtos(List<HoraireOuvertureEmbeddable> entities);

    HoraireOuvertureEmbeddable toEntity(HoraireOuverture dto);

    List<HoraireOuvertureEmbeddable> toEntities(List<HoraireOuverture> dtos);
}