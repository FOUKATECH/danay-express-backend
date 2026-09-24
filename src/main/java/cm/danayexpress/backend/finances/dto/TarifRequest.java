package cm.danayexpress.backend.finances.dto;

import cm.danayexpress.backend.finances.enums.StatutTarif;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TarifRequest(

        @NotNull(message = "L'id de l'agence d'origine est obligatoire")
        Long origineId,

        @NotNull(message = "L'id de l'agence de destination est obligatoire")
        Long destinationId,

        @NotBlank(message = "Le type de véhicule est obligatoire")
        @Size(max = 50)
        String typeVehicule,

        @NotNull(message = "Le montant est obligatoire")
        @DecimalMin(value = "0", message = "Le montant doit être positif ou nul")
        BigDecimal montant,

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        /** Null = tarif applicable indéfiniment. */
        LocalDate dateFin,

        /** Optionnel : si non fourni à la création, ACTIF est appliqué par défaut. */
        StatutTarif statut
) {
}