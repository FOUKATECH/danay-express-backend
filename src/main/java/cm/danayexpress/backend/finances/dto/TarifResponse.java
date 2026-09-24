package cm.danayexpress.backend.finances.dto;

import cm.danayexpress.backend.finances.enums.StatutTarif;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record TarifResponse(
        Long id,
        Long origineId,
        String origineNom,
        Long destinationId,
        String destinationNom,
        String typeVehicule,
        BigDecimal montant,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutTarif statut,
        Instant createdAt,
        Instant updatedAt
) {
}