package cm.danayexpress.backend.parcautomobile.dto;

import java.time.Instant;

public record ProprietaireResponse(
        Long id,
        String nom,
        String telephone,
        String adresse,
        Instant createdAt,
        Instant updatedAt
) {
}