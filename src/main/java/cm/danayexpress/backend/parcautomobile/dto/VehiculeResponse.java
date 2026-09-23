package cm.danayexpress.backend.parcautomobile.dto;

import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;

import java.time.Instant;

public record VehiculeResponse(
        Long id,
        String immatriculation,
        String type,
        Integer capacite,
        Long proprietaireId,
        String proprietaireNom,
        Long agenceId,
        String agenceNom,
        StatutVehicule statut,
        Instant createdAt,
        Instant updatedAt
) {
}