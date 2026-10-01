package com.pgvpt.service;

import com.pgvpt.model.ActiviteEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ActiviteService {

    Page<ActiviteEntity> rechercher(int page, int size, String code, String libelle);

    ActiviteEntity creer(ActiviteEntity request);

    ActiviteEntity rechercher(UUID id);

    ActiviteEntity modifier(UUID id, ActiviteEntity request);

    void supprimer(UUID id);

    ActiviteEntity activer(UUID id);

    ActiviteEntity desactiver(UUID id);
}
