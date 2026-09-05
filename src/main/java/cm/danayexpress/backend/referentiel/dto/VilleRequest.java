package cm.danayexpress.backend.referentiel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Données envoyées par le client pour créer ou modifier une Ville.
 * Un "record" Java : classe immuable, concise, idéale pour des DTO.
 */
public record VilleRequest(

        @NotBlank(message = "Le nom de la ville est obligatoire")
        @Size(max = 150, message = "Le nom ne doit pas dépasser 150 caractères")
        String nom,

        @Size(max = 150, message = "La région ne doit pas dépasser 150 caractères")
        String region
) {
}