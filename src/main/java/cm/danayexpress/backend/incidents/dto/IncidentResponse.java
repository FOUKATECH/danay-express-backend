package cm.danayexpress.backend.incidents.dto;

import cm.danayexpress.backend.incidents.enums.GraviteIncident;
import cm.danayexpress.backend.incidents.enums.StatutIncident;
import cm.danayexpress.backend.incidents.enums.TypeIncident;

import java.time.Instant;

public record IncidentResponse(
        Long id,
        Long vehiculeId,
        String immatriculationVehicule,
        Long voyageId,
        Long agenceId,
        String nomAgence,
        String localisationDeclaree,
        Instant dateHeureIncident,
        TypeIncident typeIncident,
        GraviteIncident gravite,
        String description,
        Integer nombrePassagersConcernes,
        StatutIncident statut,
        Instant createdAt,
        Instant updatedAt
) {
}
