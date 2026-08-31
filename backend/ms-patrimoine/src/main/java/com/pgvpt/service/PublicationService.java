package com.pgvpt.service;

import com.pgvpt.dto.Patrimoine;
import java.util.UUID;

public interface PublicationService {
    Patrimoine depublierPatrimoine(UUID id);
    Patrimoine publierPatrimoine(UUID id);
}
