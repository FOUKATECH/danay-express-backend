package cm.danayexpress.backend.referentiel.dto;

import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AgenceRequest(

        @NotBlank(message = "Le code de l'agence est obligatoire")
        @Size(max = 20, message = "Le code ne doit pas dépasser 20 caractères")
        String code,

        @NotBlank(message = "Le nom de l'agence est obligatoire")
        @Size(max = 150)
        String nom,

        @NotNull(message = "L'id de la ville est obligatoire")
        Long villeId,

        @Size(max = 255)
        String adresse,

        @Size(max = 30)
        String telephone,

        @Size(max = 150)
        String responsable,

        /** Optionnel : si non fourni à la création, ACTIVE est appliqué par défaut. */
        StatutReferentiel statut
) {
}