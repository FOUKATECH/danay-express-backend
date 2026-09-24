package cm.danayexpress.backend.finances.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record RecetteResponse(
        Long id,
        Long mouvementTransitId,
        Long voyageId,
        String agenceOrigineNom,
        String agenceMouvementNom,
        Integer nombrePassagers,
        Long tarifId,
        BigDecimal montantUnitaire,
        BigDecimal montant,
        Instant createdAt
) {
}