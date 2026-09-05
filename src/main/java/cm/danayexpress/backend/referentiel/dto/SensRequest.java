package cm.danayexpress.backend.referentiel.dto;

import cm.danayexpress.backend.referentiel.enums.SensCode;
import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;
import jakarta.validation.constraints.NotNull;

public record SensRequest(

        @NotNull(message = "L'id de la ligne est obligatoire")
        Long ligneId,

        @NotNull(message = "Le code du sens (ALLER ou RETOUR) est obligatoire")
        SensCode code,

        @NotNull(message = "L'id de l'agence de départ est obligatoire")
        Long agenceDepartId,

        @NotNull(message = "L'id de l'agence d'arrivée est obligatoire")
        Long agenceArriveeId,

        /** Optionnel : si non fourni à la création, ACTIVE est appliqué par défaut. */
        StatutReferentiel statut
) {
}