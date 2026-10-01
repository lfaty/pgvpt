package com.pgvpt.record;

import java.util.UUID;

public record GeolocalisationResponse(UUID patrimoineId, Double latitude, Double longitude) {
}
