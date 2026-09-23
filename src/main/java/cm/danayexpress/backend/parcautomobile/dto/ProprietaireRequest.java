package cm.danayexpress.backend.parcautomobile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProprietaireRequest(

        @NotBlank(message = "Le nom du propriétaire est obligatoire")
        @Size(max = 150)
        String nom,

        @Size(max = 30)
        String telephone,

        @Size(max = 255)
        String adresse
) {
}