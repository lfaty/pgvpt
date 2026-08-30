package com.pgvpt.service;

import com.pgvpt.dto.Monument;
import com.pgvpt.dto.MonumentCreate;
import com.pgvpt.dto.MonumentUpdate;

import java.util.UUID;

public interface MonumentService {

    Monument create(MonumentCreate request);

    Monument findById(UUID id);

    Monument update(UUID id, MonumentUpdate request);
}