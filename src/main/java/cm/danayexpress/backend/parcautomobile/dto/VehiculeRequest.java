package cm.danayexpress.backend.parcautomobile.dto;

import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VehiculeRequest(

        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20)
        String immatriculation,

        @Size(max = 50)
        String type,

        @NotNull(message = "La capacité est obligatoire")
        @Min(value = 1, message = "La capacité doit être supérieure ou égale à 1")
        Integer capacite,

        @NotNull(message = "L'id du propriétaire est obligatoire")
        Long proprietaireId,

        @NotNull(message = "L'id de l'agence est obligatoire")
        Long agenceId,

        /** Optionnel : si non fourni à la création, DISPONIBLE est appliqué par défaut. */
        StatutVehicule statut
) {
}