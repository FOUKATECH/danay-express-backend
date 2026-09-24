package cm.danayexpress.backend.administration.service;

import cm.danayexpress.backend.administration.dto.ChangePasswordRequest;
import cm.danayexpress.backend.administration.dto.LoginRequest;
import cm.danayexpress.backend.administration.dto.LoginResponse;
import cm.danayexpress.backend.administration.dto.UserResponse;
import cm.danayexpress.backend.administration.entity.Permission;
import cm.danayexpress.backend.administration.entity.Utilisateur;
import cm.danayexpress.backend.administration.enums.StatutUtilisateur;
import cm.danayexpress.backend.administration.exception.AdministrationConflictException;
import cm.danayexpress.backend.administration.exception.AdministrationNotFoundException;
import cm.danayexpress.backend.administration.mapper.UserMapper;
import cm.danayexpress.backend.administration.repository.UtilisateurRepository;
import cm.danayexpress.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UtilisateurRepository utilisateurRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        log.info("Tentative de connexion pour l'utilisateur : {}", request.username());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(request.username())
                .orElseThrow(() -> new AdministrationNotFoundException("Utilisateur", request.username()));

        if (utilisateur.getStatut() != StatutUtilisateur.ACTIF) {
            throw new AdministrationConflictException("Le compte de cet utilisateur n'est pas actif (statut: " + utilisateur.getStatut() + ")");
        }

        // Mettre à jour la date de dernier login
        utilisateur.setDernierLogin(Instant.now());
        utilisateurRepository.save(utilisateur);

        String roleCode = utilisateur.getRole() != null ? utilisateur.getRole().getCode() : "USER";
        String token = tokenProvider.generateToken(utilisateur.getNomUtilisateur(), roleCode);

        Set<String> permissions = new HashSet<>();
        if (utilisateur.getRole() != null && utilisateur.getRole().getPermissions() != null) {
            permissions = utilisateur.getRole().getPermissions().stream()
                    .map(Permission::getCode)
                    .collect(Collectors.toSet());
        }

        UserResponse userResponse = userMapper.toResponse(utilisateur);
        log.info("Connexion réussie pour l'utilisateur ID={}", utilisateur.getId());

        return new LoginResponse(token, "Bearer", tokenProvider.getExpirationInSeconds(), userResponse, permissions);
    }

    @Override
    public UserResponse getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(username)
                .orElseThrow(() -> new AdministrationNotFoundException("Utilisateur", username));

        return userMapper.toResponse(utilisateur);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(username)
                .orElseThrow(() -> new AdministrationNotFoundException("Utilisateur", username));

        if (!passwordEncoder.matches(request.ancienMotDePasse(), utilisateur.getMotDePasse())) {
            throw new AdministrationConflictException("L'ancien mot de passe fourni est incorrect.");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(request.nouveauMotDePasse()));
        utilisateurRepository.save(utilisateur);
        log.info("Mot de passe mis à jour pour l'utilisateur {}", username);
    }
}
