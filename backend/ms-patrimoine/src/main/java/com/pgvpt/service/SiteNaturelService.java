package com.pgvpt.service;

import com.pgvpt.dto.SiteNaturel;
import com.pgvpt.dto.SiteNaturelCreate;
import com.pgvpt.dto.SiteNaturelUpdate;

import java.util.UUID;

public interface SiteNaturelService {

    SiteNaturel create(SiteNaturelCreate request);

    SiteNaturel findById(UUID id);

    SiteNaturel update(UUID id, SiteNaturelUpdate request);
}