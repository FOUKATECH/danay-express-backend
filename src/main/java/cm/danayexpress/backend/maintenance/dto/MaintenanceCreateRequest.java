package cm.danayexpress.backend.maintenance.dto;

import cm.danayexpress.backend.maintenance.enums.StatutInterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.TypeInterventionMaintenance;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record MaintenanceCreateRequest(

        @NotNull(message = "L'id du véhicule est obligatoire")
        Long vehiculeId,

        Long incidentId,

        @NotNull(message = "Le type d'intervention est obligatoire")
        TypeInterventionMaintenance typeIntervention,

        StatutInterventionMaintenance statut,

        @NotBlank(message = "Le motif ou la description de la panne est obligatoire")
        String descriptionPanneMotif,

        String travauxRealises,

        @Size(max = 255)
        String garagePrestataire,

        @DecimalMin(value = "0", message = "Le coût total doit être supérieur ou égal à 0")
        BigDecimal coutTotal,

        Instant dateEntree,

        Instant dateSortiePrevue
) {
}
