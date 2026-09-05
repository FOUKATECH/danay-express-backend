package cm.danayexpress.backend.referentiel.dto;

import cm.danayexpress.backend.referentiel.enums.SensCode;
import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;

import java.time.Instant;

public record SensResponse(
        Long id,
        Long ligneId,
        String ligneNom,
        SensCode code,
        Long agenceDepartId,
        String agenceDepartNom,
        Long agenceArriveeId,
        String agenceArriveeNom,
        StatutReferentiel statut,
        Instant createdAt,
        Instant updatedAt
) {
}