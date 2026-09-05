package cm.danayexpress.backend.referentiel.dto;

import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LigneRequest(

        @NotBlank(message = "Le code de la ligne est obligatoire")
        @Size(max = 20)
        String code,

        @NotBlank(message = "Le nom de la ligne est obligatoire")
        @Size(max = 150)
        String nom,

        String description,

        /** Optionnel : si non fourni à la création, ACTIVE est appliqué par défaut. */
        StatutReferentiel statut
) {
}