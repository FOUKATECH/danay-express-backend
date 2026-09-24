package cm.danayexpress.backend.incidents.dto;

import cm.danayexpress.backend.incidents.enums.GraviteIncident;
import cm.danayexpress.backend.incidents.enums.TypeIncident;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record IncidentCreateRequest(

        @NotNull(message = "L'id du véhicule est obligatoire")
        Long vehiculeId,

        Long voyageId,

        Long agenceId,

        @Size(max = 255, message = "La localisation déclarée ne doit pas dépasser 255 caractères")
        String localisationDeclaree,

        Instant dateHeureIncident,

        @NotNull(message = "Le type d'incident est obligatoire")
        TypeIncident typeIncident,

        @NotNull(message = "La gravité de l'incident est obligatoire")
        GraviteIncident gravite,

        String description,

        @Min(value = 0, message = "Le nombre de passagers concernés doit être supérieur ou égal à 0")
        Integer nombrePassagersConcernes
) {
}
