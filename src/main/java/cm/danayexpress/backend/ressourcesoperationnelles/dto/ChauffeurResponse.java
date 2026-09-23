package cm.danayexpress.backend.ressourcesoperationnelles.dto;

import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutChauffeur;

import java.time.Instant;

public record ChauffeurResponse(
        Long id,
        String nom,
        String prenom,
        String telephone,
        String numeroPermis,
        StatutChauffeur statut,
        Instant createdAt,
        Instant updatedAt
) {
}