package cm.danayexpress.backend.incidents.dto;

import cm.danayexpress.backend.incidents.enums.StatutInterventionSecours;
import jakarta.validation.constraints.NotNull;

public record ChangerStatutInterventionRequest(

        @NotNull(message = "Le statut est obligatoire")
        StatutInterventionSecours nouveauStatut,

        String observations
) {
}
