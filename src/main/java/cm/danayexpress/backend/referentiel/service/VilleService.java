package cm.danayexpress.backend.referentiel.service;

import cm.danayexpress.backend.referentiel.dto.VilleRequest;
import cm.danayexpress.backend.referentiel.dto.VilleResponse;
import cm.danayexpress.backend.referentiel.entity.Ville;
import cm.danayexpress.backend.referentiel.exception.ReferentielNotFoundException;
import cm.danayexpress.backend.referentiel.mapper.VilleMapper;
import cm.danayexpress.backend.referentiel.repository.VilleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Logique métier du référentiel des villes. Le contrôleur ne fait
 * qu'appeler ces méthodes : toute règle de gestion (validation,
 * vérifications d'existence, etc.) est centralisée ici, jamais dans le
 * contrôleur ni dans le repository.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VilleService {

    private final VilleRepository villeRepository;
    private final VilleMapper villeMapper;

    public List<VilleResponse> findAll() {
        return villeRepository.findAll().stream()
                .map(villeMapper::toResponse)
                .toList();
    }

    public VilleResponse findById(Long id) {
        return villeMapper.toResponse(getVilleOrThrow(id));
    }

    @Transactional
    public VilleResponse create(VilleRequest request) {
        Ville ville = villeMapper.toEntity(request);
        Ville saved = villeRepository.save(ville);
        return villeMapper.toResponse(saved);
    }

    @Transactional
    public VilleResponse update(Long id, VilleRequest request) {
        Ville ville = getVilleOrThrow(id);
        villeMapper.updateEntityFromRequest(request, ville);
        Ville saved = villeRepository.save(ville);
        return villeMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Ville ville = getVilleOrThrow(id);
        villeRepository.delete(ville);
    }

    private Ville getVilleOrThrow(Long id) {
        return villeRepository.findById(id)
                .orElseThrow(() -> new ReferentielNotFoundException("Ville", id));
    }
}