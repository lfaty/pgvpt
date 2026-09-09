package com.pgvpt.contenu.mapper;

import com.pgvpt.dto.Contenu;
import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.dto.ContenuCreate;
import com.pgvpt.dto.ContenuUpdate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContenuApiMapper {

    Contenu toApi(ContenuEntity entity);

    List<Contenu> toApi(List<ContenuEntity> entity);

    ContenuEntity toEntity(Contenu dto);

    ContenuEntity toEntity(ContenuCreate dto);

    ContenuEntity toEntity(ContenuUpdate dto);
}
