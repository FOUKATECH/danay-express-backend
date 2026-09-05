package cm.danayexpress.backend.referentiel.dto;

import java.time.Instant;

/**
 * Données renvoyées au client pour une Ville.
 * On n'expose jamais directement l'entité JPA {@code Ville} en dehors
 * du service : ce DTO découple le contrat de l'API du modèle de
 * persistance (une colonne technique peut changer sans casser l'API,
 * et inversement).
 */
public record VilleResponse(
        Long id,
        String nom,
        String region,
        Instant createdAt,
        Instant updatedAt
) {
}