package cm.danayexpress.backend.ressourcesoperationnelles.dto;

import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutChauffeur;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChauffeurRequest(

        @NotBlank(message = "Le nom du chauffeur est obligatoire")
        @Size(max = 150)
        String nom,

        @Size(max = 150)
        String prenom,

        @Size(max = 30)
        String telephone,

        @NotBlank(message = "Le numéro de permis est obligatoire")
        @Size(max = 30)
        String numeroPermis,

        /** Optionnel : si non fourni à la création, ACTIF est appliqué par défaut. */
        StatutChauffeur statut
) {
}