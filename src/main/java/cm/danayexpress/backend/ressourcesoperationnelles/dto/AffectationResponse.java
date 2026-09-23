package cm.danayexpress.backend.ressourcesoperationnelles.dto;

import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutAffectation;

import java.time.Instant;
import java.time.LocalDate;

public record AffectationResponse(
        Long id,
        Long chauffeurId,
        String chauffeurNom,
        String chauffeurPrenom,
        Long vehiculeId,
        String vehiculeImmatriculation,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutAffectation statut,
        Instant createdAt,
        Instant updatedAt
) {
}