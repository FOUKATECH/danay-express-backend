package cm.danayexpress.backend.incidents.dto;

import jakarta.validation.constraints.NotNull;

public record AffecterSecoursRequest(

        @NotNull(message = "L'id du véhicule de secours est obligatoire")
        Long vehiculeSecoursId,

        Long chauffeurSecoursId,

        String observations
) {
}
