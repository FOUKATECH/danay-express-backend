package cm.danayexpress.backend.passagers.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Une ligne de répartition d'une descente : combien de passagers viennent de quelle agence d'origine. */
public record RepartitionOrigineRequest(

        @NotNull(message = "L'id de l'agence d'origine est obligatoire")
        Long agenceOrigineId,

        @NotNull(message = "Le nombre de passagers est obligatoire")
        @Min(value = 1, message = "Le nombre de passagers doit être supérieur ou égal à 1")
        Integer nombre
) {
}