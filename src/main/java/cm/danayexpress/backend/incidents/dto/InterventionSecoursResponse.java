package cm.danayexpress.backend.incidents.dto;

import cm.danayexpress.backend.incidents.enums.StatutInterventionSecours;

import java.time.Instant;

public record InterventionSecoursResponse(
        Long id,
        Long incidentId,
        Long vehiculeSecoursId,
        String immatriculationVehiculeSecours,
        Long chauffeurSecoursId,
        String nomCompletChauffeurSecours,
        Instant dateHeureAffectation,
        Instant dateHeureDepart,
        Instant dateHeurePriseEnCharge,
        Instant dateHeureResolution,
        StatutInterventionSecours statut,
        String observations,
        Instant createdAt,
        Instant updatedAt
) {
}
