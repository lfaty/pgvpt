package com.pgvpt.service;

import com.pgvpt.dto.Musee;
import com.pgvpt.dto.MuseeCreate;
import com.pgvpt.dto.MuseeUpdate;

import java.util.UUID;

public interface MuseeService {

    Musee create(MuseeCreate request);

    Musee findById(UUID id);

    Musee update(UUID id, MuseeUpdate request);
}