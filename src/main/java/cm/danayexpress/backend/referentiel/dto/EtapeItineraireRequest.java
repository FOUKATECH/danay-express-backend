package cm.danayexpress.backend.referentiel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EtapeItineraireRequest(

        @NotNull(message = "L'id du sens est obligatoire")
        Long sensId,

        @NotNull(message = "L'id de l'agence est obligatoire")
        Long agenceId,

        @NotNull(message = "L'ordre est obligatoire")
        @Min(value = 1, message = "L'ordre doit être supérieur ou égal à 1")
        Integer ordre
) {
}