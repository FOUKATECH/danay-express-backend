package cm.danayexpress.backend.administration.service;

import cm.danayexpress.backend.administration.dto.UserCreateRequest;
import cm.danayexpress.backend.administration.dto.UserResponse;
import cm.danayexpress.backend.administration.dto.UserUpdateRequest;
import cm.danayexpress.backend.administration.entity.Role;
import cm.danayexpress.backend.administration.entity.Utilisateur;
import cm.danayexpress.backend.administration.enums.StatutUtilisateur;
import cm.danayexpress.backend.administration.exception.AdministrationConflictException;
import cm.danayexpress.backend.administration.exception.AdministrationNotFoundException;
import cm.danayexpress.backend.administration.mapper.UserMapper;
import cm.danayexpress.backend.administration.repository.RoleRepository;
import cm.danayexpress.backend.administration.repository.UtilisateurRepository;
import cm.danayexpress.backend.referentiel.entity.Agence;
import cm.danayexpress.backend.referentiel.repository.AgenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final AgenceRepository agenceRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse creerUtilisateur(UserCreateRequest request) {
        log.info("Création d'un nouvel utilisateur username={}", request.nomUtilisateur());

        if (utilisateurRepository.existsByNomUtilisateur(request.nomUtilisateur())) {
            throw new AdministrationConflictException("Le nom d'utilisateur '" + request.nomUtilisateur() + "' est déjà utilisé");
        }

        if (request.email() != null && !request.email().isBlank() && utilisateurRepository.existsByEmail(request.email())) {
            throw new AdministrationConflictException("L'email '" + request.email() + "' est déjà utilisé");
        }

        Role role = roleRepository.findById(request.roleId())
                .orElseThrow(() -> new AdministrationNotFoundException("Rôle", request.roleId()));

        Agence agence = null;
        if (request.agenceId() != null) {
            agence = agenceRepository.findById(request.agenceId())
                    .orElseThrow(() -> new AdministrationNotFoundException("Agence", request.agenceId()));
        }

        Utilisateur utilisateur = userMapper.toEntity(request);
        utilisateur.setRole(role);
        utilisateur.setAgence(agence);
        utilisateur.setMotDePasse(passwordEncoder.encode(request.motDePasse()));
        if (utilisateur.getStatut() == null) {
            utilisateur.setStatut(StatutUtilisateur.ACTIF);
        }

        Utilisateur saved = utilisateurRepository.save(utilisateur);
        log.info("Utilisateur créé avec succès ID={}", saved.getId());

        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public UserResponse modifierUtilisateur(Long id, UserUpdateRequest request) {
        Utilisateur utilisateur = findEntityById(id);

        if (request.email() != null && !request.email().equals(utilisateur.getEmail())) {
            if (utilisateurRepository.existsByEmail(request.email())) {
                throw new AdministrationConflictException("L'email '" + request.email() + "' est déjà utilisé par un autre compte");
            }
        }

        if (request.roleId() != null) {
            Role role = roleRepository.findById(request.roleId())
                    .orElseThrow(() -> new AdministrationNotFoundException("Rôle", request.roleId()));
            utilisateur.setRole(role);
        }

        if (request.agenceId() != null) {
            Agence agence = agenceRepository.findById(request.agenceId())
                    .orElseThrow(() -> new AdministrationNotFoundException("Agence", request.agenceId()));
            utilisateur.setAgence(agence);
        }

        userMapper.updateEntityFromRequest(request, utilisateur);
        Utilisateur saved = utilisateurRepository.save(utilisateur);

        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse getUtilisateurById(Long id) {
        return userMapper.toResponse(findEntityById(id));
    }

    @Override
    public List<UserResponse> listerUtilisateurs(StatutUtilisateur statut, Long roleId, Long agenceId) {
        List<Utilisateur> list = utilisateurRepository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();
            if (statut != null) {
                predicates.getExpressions().add(cb.equal(root.get("statut"), statut));
            }
            if (roleId != null) {
                predicates.getExpressions().add(cb.equal(root.get("role").get("id"), roleId));
            }
            if (agenceId != null) {
                predicates.getExpressions().add(cb.equal(root.get("agence").get("id"), agenceId));
            }
            return predicates;
        });

        return list.stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse changerStatut(Long id, StatutUtilisateur statut) {
        Utilisateur utilisateur = findEntityById(id);
        utilisateur.setStatut(statut);
        Utilisateur saved = utilisateurRepository.save(utilisateur);
        log.info("Statut de l'utilisateur ID={} mis à jour : {}", id, statut);
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void reinitialiserMotDePasse(Long id, String nouveauMotDePasse) {
        Utilisateur utilisateur = findEntityById(id);
        utilisateur.setMotDePasse(passwordEncoder.encode(nouveauMotDePasse));
        utilisateurRepository.save(utilisateur);
        log.info("Mot de passe réinitialisé pour l'utilisateur ID={}", id);
    }

    private Utilisateur findEntityById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new AdministrationNotFoundException("Utilisateur", id));
    }
}
