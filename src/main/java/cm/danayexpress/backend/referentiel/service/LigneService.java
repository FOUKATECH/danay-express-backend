package cm.danayexpress.backend.referentiel.service;

import cm.danayexpress.backend.referentiel.dto.LigneRequest;
import cm.danayexpress.backend.referentiel.dto.LigneResponse;
import cm.danayexpress.backend.referentiel.entity.Ligne;
import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;
import cm.danayexpress.backend.referentiel.exception.ReferentielConflictException;
import cm.danayexpress.backend.referentiel.exception.ReferentielNotFoundException;
import cm.danayexpress.backend.referentiel.mapper.LigneMapper;
import cm.danayexpress.backend.referentiel.repository.LigneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LigneService {

    private final LigneRepository ligneRepository;
    private final LigneMapper ligneMapper;

    public List<LigneResponse> findAll() {
        return ligneRepository.findAll().stream()
                .map(ligneMapper::toResponse)
                .toList();
    }

    public LigneResponse findById(Long id) {
        return ligneMapper.toResponse(getLigneOrThrow(id));
    }

    @Transactional
    public LigneResponse create(LigneRequest request) {
        ensureCodeIsAvailable(request.code(), null);

        Ligne ligne = ligneMapper.toEntity(request);
        ligne.setStatut(request.statut() != null ? request.statut() : StatutReferentiel.ACTIVE);

        return ligneMapper.toResponse(ligneRepository.save(ligne));
    }

    @Transactional
    public LigneResponse update(Long id, LigneRequest request) {
        Ligne ligne = getLigneOrThrow(id);
        ensureCodeIsAvailable(request.code(), id);

        ligneMapper.updateEntityFromRequest(request, ligne);
        ligne.setStatut(request.statut() != null ? request.statut() : ligne.getStatut());

        return ligneMapper.toResponse(ligneRepository.save(ligne));
    }

    @Transactional
    public void delete(Long id) {
        Ligne ligne = getLigneOrThrow(id);
        ligneRepository.delete(ligne);
    }

    private Ligne getLigneOrThrow(Long id) {
        return ligneRepository.findById(id)
                .orElseThrow(() -> new ReferentielNotFoundException("Ligne", id));
    }

    private void ensureCodeIsAvailable(String code, Long excludedLigneId) {
        ligneRepository.findByCode(code).ifPresent(existing -> {
            if (!existing.getId().equals(excludedLigneId)) {
                throw new ReferentielConflictException("Le code de ligne '" + code + "' est déjà utilisé.");
            }
        });
    }
}