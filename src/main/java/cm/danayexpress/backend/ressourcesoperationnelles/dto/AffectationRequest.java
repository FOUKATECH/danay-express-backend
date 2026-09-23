package cm.danayexpress.backend.ressourcesoperationnelles.dto;

import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutAffectation;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AffectationRequest(

        @NotNull(message = "L'id du chauffeur est obligatoire")
        Long chauffeurId,

        @NotNull(message = "L'id du véhicule est obligatoire")
        Long vehiculeId,

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        /** Optionnelle : renseignée seulement quand l'affectation est terminée. */
        LocalDate dateFin,

        /** Optionnel : si non fourni à la création, ACTIVE est appliqué par défaut. */
        StatutAffectation statut
) {
}