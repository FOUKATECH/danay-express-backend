package cm.danayexpress.backend.administration.controller;

import cm.danayexpress.backend.administration.dto.UserCreateRequest;
import cm.danayexpress.backend.administration.dto.UserResponse;
import cm.danayexpress.backend.administration.dto.UserUpdateRequest;
import cm.danayexpress.backend.administration.enums.StatutUtilisateur;
import cm.danayexpress.backend.administration.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/administration/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @PostMapping
    public ResponseEntity<UserResponse> creerUtilisateur(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurService.creerUtilisateur(request));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listerUtilisateurs(
            @RequestParam(required = false) StatutUtilisateur statut,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) Long agenceId) {
        return ResponseEntity.ok(utilisateurService.listerUtilisateurs(statut, roleId, agenceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUtilisateurById(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> modifierUtilisateur(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(utilisateurService.modifierUtilisateur(id, request));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<UserResponse> changerStatut(
            @PathVariable Long id,
            @RequestParam StatutUtilisateur statut) {
        return ResponseEntity.ok(utilisateurService.changerStatut(id, statut));
    }

    @PostMapping("/{id}/reinitialiser-mot-de-passe")
    public ResponseEntity<Void> reinitialiserMotDePasse(
            @PathVariable Long id,
            @RequestParam String nouveauMotDePasse) {
        utilisateurService.reinitialiserMotDePasse(id, nouveauMotDePasse);
        return ResponseEntity.noContent().build();
    }
}
