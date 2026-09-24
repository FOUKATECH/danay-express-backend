package cm.danayexpress.backend.administration.service;

import cm.danayexpress.backend.administration.dto.UserCreateRequest;
import cm.danayexpress.backend.administration.dto.UserResponse;
import cm.danayexpress.backend.administration.dto.UserUpdateRequest;
import cm.danayexpress.backend.administration.enums.StatutUtilisateur;

import java.util.List;

/**
 * Service de gestion des comptes utilisateurs (CDC section 8.13).
 */
public interface UtilisateurService {

    UserResponse creerUtilisateur(UserCreateRequest request);

    UserResponse modifierUtilisateur(Long id, UserUpdateRequest request);

    UserResponse getUtilisateurById(Long id);

    List<UserResponse> listerUtilisateurs(StatutUtilisateur statut, Long roleId, Long agenceId);

    UserResponse changerStatut(Long id, StatutUtilisateur statut);

    void reinitialiserMotDePasse(Long id, String nouveauMotDePasse);
}
