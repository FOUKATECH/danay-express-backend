package cm.danayexpress.backend.passagers.dto;

import cm.danayexpress.backend.passagers.enums.TypeMouvement;

import java.time.Instant;

public record MouvementTransitResponse(
        Long id,
        Long voyageId,
        Long escaleId,
        Long agenceOrigineId,
        String agenceOrigineNom,
        Long agenceMouvementId,
        String agenceMouvementNom,
        TypeMouvement typeMouvement,
        Integer nombrePassagers,
        Instant createdAt
) {
}