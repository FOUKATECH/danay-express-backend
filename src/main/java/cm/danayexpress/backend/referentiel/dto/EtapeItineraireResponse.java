package cm.danayexpress.backend.referentiel.dto;

import java.time.Instant;

public record EtapeItineraireResponse(
        Long id,
        Long sensId,
        Long agenceId,
        String agenceNom,
        Integer ordre,
        Instant createdAt,
        Instant updatedAt
) {
}