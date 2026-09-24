package cm.danayexpress.backend.incidents.dto;

import cm.danayexpress.backend.incidents.enums.GraviteIncident;
import cm.danayexpress.backend.incidents.enums.StatutIncident;
import cm.danayexpress.backend.incidents.enums.TypeIncident;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record IncidentUpdateRequest(

        Long agenceId,

        @Size(max = 255)
        String localisationDeclaree,

        TypeIncident typeIncident,

        GraviteIncident gravite,

        String description,

        @Min(0)
        Integer nombrePassagersConcernes,

        StatutIncident statut
) {
}
