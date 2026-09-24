package cm.danayexpress.backend.administration.dto;

import cm.danayexpress.backend.administration.enums.StatutUtilisateur;
import jakarta.validation.constraints.Email;

public record UserUpdateRequest(

        @Email(message = "Format d'email invalide")
        String email,

        String nom,

        String prenom,

        String telephone,

        Long roleId,

        Long agenceId,

        StatutUtilisateur statut
) {
}
