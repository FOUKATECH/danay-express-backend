package cm.danayexpress.backend.maintenance.dto;

import cm.danayexpress.backend.maintenance.enums.StatutInterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.TypeInterventionMaintenance;

import java.math.BigDecimal;
import java.time.Instant;

public record MaintenanceResponse(
        Long id,
        Long vehiculeId,
        String immatriculationVehicule,
        Long incidentId,
        TypeInterventionMaintenance typeIntervention,
        StatutInterventionMaintenance statut,
        String descriptionPanneMotif,
        String travauxRealises,
        String garagePrestataire,
        BigDecimal coutTotal,
        Instant dateEntree,
        Instant dateSortiePrevue,
        Instant dateSortieReelle,
        Instant createdAt,
        Instant updatedAt
) {
}
