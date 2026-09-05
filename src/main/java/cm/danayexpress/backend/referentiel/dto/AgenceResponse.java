package cm.danayexpress.backend.referentiel.dto;

import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;

import java.time.Instant;

public record AgenceResponse(
        Long id,
        String code,
        String nom,
        Long villeId,
        String villeNom,
        String adresse,
        String telephone,
        String responsable,
        StatutReferentiel statut,
        Instant createdAt,
        Instant updatedAt
) {
}