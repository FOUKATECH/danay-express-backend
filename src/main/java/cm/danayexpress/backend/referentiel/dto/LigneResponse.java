package cm.danayexpress.backend.referentiel.dto;

import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;

import java.time.Instant;

public record LigneResponse(
        Long id,
        String code,
        String nom,
        String description,
        StatutReferentiel statut,
        Instant createdAt,
        Instant updatedAt
) {
}