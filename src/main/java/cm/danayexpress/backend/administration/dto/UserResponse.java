package cm.danayexpress.backend.administration.dto;

import cm.danayexpress.backend.administration.enums.StatutUtilisateur;

import java.time.Instant;

public record UserResponse(
        Long id,
        String nomUtilisateur,
        String email,
        String nom,
        String prenom,
        String telephone,
        StatutUtilisateur statut,
        Long roleId,
        String roleCode,
        String roleLibelle,
        Long agenceId,
        String agenceNom,
        Instant dernierLogin,
        Instant createdAt,
        Instant updatedAt
) {
}
