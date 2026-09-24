package cm.danayexpress.backend.maintenance.dto;

import cm.danayexpress.backend.maintenance.enums.StatutInterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.TypeInterventionMaintenance;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record MaintenanceUpdateRequest(

        TypeInterventionMaintenance typeIntervention,

        StatutInterventionMaintenance statut,

        String descriptionPanneMotif,

        String travauxRealises,

        @Size(max = 255)
        String garagePrestataire,

        @DecimalMin(value = "0", message = "Le coût total doit être positif ou nul")
        BigDecimal coutTotal,

        Instant dateEntree,

        Instant dateSortiePrevue,

        Instant dateSortieReelle
) {
}
