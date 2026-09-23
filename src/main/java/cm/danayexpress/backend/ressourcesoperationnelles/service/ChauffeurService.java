package cm.danayexpress.backend.ressourcesoperationnelles.service;

import cm.danayexpress.backend.ressourcesoperationnelles.dto.ChauffeurRequest;
import cm.danayexpress.backend.ressourcesoperationnelles.dto.ChauffeurResponse;
import cm.danayexpress.backend.ressourcesoperationnelles.entity.Chauffeur;
import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutChauffeur;
import cm.danayexpress.backend.ressourcesoperationnelles.exception.RessourcesOperationnellesConflictException;
import cm.danayexpress.backend.ressourcesoperationnelles.exception.RessourcesOperationnellesNotFoundException;
import cm.danayexpress.backend.ressourcesoperationnelles.mapper.ChauffeurMapper;
import cm.danayexpress.backend.ressourcesoperationnelles.repository.ChauffeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChauffeurService {

    private final ChauffeurRepository chauffeurRepository;
    private final ChauffeurMapper chauffeurMapper;

    public List<ChauffeurResponse> findAll() {
        return chauffeurRepository.findAll().stream()
                .map(chauffeurMapper::toResponse)
                .toList();
    }

    public List<ChauffeurResponse> findByStatut(StatutChauffeur statut) {
        return chauffeurRepository.findByStatut(statut).stream()
                .map(chauffeurMapper::toResponse)
                .toList();
    }

    public ChauffeurResponse findById(Long id) {
        return chauffeurMapper.toResponse(getChauffeurOrThrow(id));
    }

    @Transactional
    public ChauffeurResponse create(ChauffeurRequest request) {
        ensureNumeroPermisIsAvailable(request.numeroPermis(), null);

        Chauffeur chauffeur = chauffeurMapper.toEntity(request);
        chauffeur.setStatut(request.statut() != null ? request.statut() : StatutChauffeur.ACTIF);

        return chauffeurMapper.toResponse(chauffeurRepository.save(chauffeur));
    }

    @Transactional
    public ChauffeurResponse update(Long id, ChauffeurRequest request) {
        Chauffeur chauffeur = getChauffeurOrThrow(id);
        ensureNumeroPermisIsAvailable(request.numeroPermis(), id);

        chauffeurMapper.updateEntityFromRequest(request, chauffeur);
        chauffeur.setStatut(request.statut() != null ? request.statut() : chauffeur.getStatut());

        return chauffeurMapper.toResponse(chauffeurRepository.save(chauffeur));
    }

    @Transactional
    public ChauffeurResponse activer(Long id) {
        Chauffeur chauffeur = getChauffeurOrThrow(id);
        chauffeur.setStatut(StatutChauffeur.ACTIF);
        return chauffeurMapper.toResponse(chauffeurRepository.save(chauffeur));
    }

    @Transactional
    public ChauffeurResponse desactiver(Long id) {
        Chauffeur chauffeur = getChauffeurOrThrow(id);
        chauffeur.setStatut(StatutChauffeur.INACTIF);
        return chauffeurMapper.toResponse(chauffeurRepository.save(chauffeur));
    }

    @Transactional
    public void delete(Long id) {
        chauffeurRepository.delete(getChauffeurOrThrow(id));
    }

    private Chauffeur getChauffeurOrThrow(Long id) {
        return chauffeurRepository.findById(id)
                .orElseThrow(() -> new RessourcesOperationnellesNotFoundException("Chauffeur", id));
    }

    private void ensureNumeroPermisIsAvailable(String numeroPermis, Long excludedChauffeurId) {
        chauffeurRepository.findByNumeroPermis(numeroPermis).ifPresent(existing -> {
            if (!existing.getId().equals(excludedChauffeurId)) {
                throw new RessourcesOperationnellesConflictException(
                        "Le numéro de permis '" + numeroPermis + "' est déjà utilisé.");
            }
        });
    }
}