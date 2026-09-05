package cm.danayexpress.backend.referentiel.service;

import cm.danayexpress.backend.referentiel.dto.AgenceRequest;
import cm.danayexpress.backend.referentiel.dto.AgenceResponse;
import cm.danayexpress.backend.referentiel.entity.Agence;
import cm.danayexpress.backend.referentiel.entity.Ville;
import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;
import cm.danayexpress.backend.referentiel.exception.ReferentielConflictException;
import cm.danayexpress.backend.referentiel.exception.ReferentielNotFoundException;
import cm.danayexpress.backend.referentiel.mapper.AgenceMapper;
import cm.danayexpress.backend.referentiel.repository.AgenceRepository;
import cm.danayexpress.backend.referentiel.repository.VilleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgenceService {

    private final AgenceRepository agenceRepository;
    private final VilleRepository villeRepository;
    private final AgenceMapper agenceMapper;

    public List<AgenceResponse> findAll() {
        return agenceRepository.findAll().stream()
                .map(agenceMapper::toResponse)
                .toList();
    }

    public AgenceResponse findById(Long id) {
        return agenceMapper.toResponse(getAgenceOrThrow(id));
    }

    @Transactional
    public AgenceResponse create(AgenceRequest request) {
        ensureCodeIsAvailable(request.code(), null);
        Ville ville = getVilleOrThrow(request.villeId());

        Agence agence = agenceMapper.toEntity(request);
        agence.setVille(ville);
        agence.setStatut(request.statut() != null ? request.statut() : StatutReferentiel.ACTIVE);

        return agenceMapper.toResponse(agenceRepository.save(agence));
    }

    @Transactional
    public AgenceResponse update(Long id, AgenceRequest request) {
        Agence agence = getAgenceOrThrow(id);
        ensureCodeIsAvailable(request.code(), id);
        Ville ville = getVilleOrThrow(request.villeId());

        agenceMapper.updateEntityFromRequest(request, agence);
        agence.setVille(ville);
        agence.setStatut(request.statut() != null ? request.statut() : agence.getStatut());

        return agenceMapper.toResponse(agenceRepository.save(agence));
    }

    @Transactional
    public void delete(Long id) {
        Agence agence = getAgenceOrThrow(id);
        agenceRepository.delete(agence);
    }

    private Agence getAgenceOrThrow(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ReferentielNotFoundException("Agence", id));
    }

    private Ville getVilleOrThrow(Long villeId) {
        return villeRepository.findById(villeId)
                .orElseThrow(() -> new ReferentielNotFoundException("Ville", villeId));
    }

    /**
     * Vérifie qu'aucune AUTRE agence n'utilise déjà ce code.
     * excludedAgenceId est null en création, ou l'id de l'agence en
     * cours de modification (pour ne pas se bloquer elle-même en update
     * si le code n'a pas changé).
     */
    private void ensureCodeIsAvailable(String code, Long excludedAgenceId) {
        agenceRepository.findByCode(code).ifPresent(existing -> {
            if (!existing.getId().equals(excludedAgenceId)) {
                throw new ReferentielConflictException("Le code d'agence '" + code + "' est déjà utilisé.");
            }
        });
    }
}