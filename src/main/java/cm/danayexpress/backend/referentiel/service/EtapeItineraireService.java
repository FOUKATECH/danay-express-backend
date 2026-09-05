package cm.danayexpress.backend.referentiel.service;

import cm.danayexpress.backend.referentiel.dto.EtapeItineraireRequest;
import cm.danayexpress.backend.referentiel.dto.EtapeItineraireResponse;
import cm.danayexpress.backend.referentiel.entity.Agence;
import cm.danayexpress.backend.referentiel.entity.EtapeItineraire;
import cm.danayexpress.backend.referentiel.entity.Sens;
import cm.danayexpress.backend.referentiel.exception.ReferentielConflictException;
import cm.danayexpress.backend.referentiel.exception.ReferentielNotFoundException;
import cm.danayexpress.backend.referentiel.mapper.EtapeItineraireMapper;
import cm.danayexpress.backend.referentiel.repository.AgenceRepository;
import cm.danayexpress.backend.referentiel.repository.EtapeItineraireRepository;
import cm.danayexpress.backend.referentiel.repository.SensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Logique métier des étapes d'itinéraire. Fait respecter RM-07 :
 * dans un même sens, un ordre ne peut être utilisé qu'une fois, et une
 * agence ne peut apparaître qu'à une seule position.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EtapeItineraireService {

    private final EtapeItineraireRepository etapeRepository;
    private final SensRepository sensRepository;
    private final AgenceRepository agenceRepository;
    private final EtapeItineraireMapper etapeMapper;

    public List<EtapeItineraireResponse> findAll() {
        return etapeRepository.findAll().stream()
                .map(etapeMapper::toResponse)
                .toList();
    }

    /** Étapes d'un sens, triées par ordre — reconstitue l'itinéraire complet. */
    public List<EtapeItineraireResponse> findBySensId(Long sensId) {
        return etapeRepository.findBySensIdOrderByOrdreAsc(sensId).stream()
                .map(etapeMapper::toResponse)
                .toList();
    }

    public EtapeItineraireResponse findById(Long id) {
        return etapeMapper.toResponse(getEtapeOrThrow(id));
    }

    @Transactional
    public EtapeItineraireResponse create(EtapeItineraireRequest request) {
        Sens sens = getSensOrThrow(request.sensId());
        Agence agence = getAgenceOrThrow(request.agenceId());
        ensureOrdreIsAvailable(request.sensId(), request.ordre(), null);
        ensureAgenceIsAvailable(request.sensId(), request.agenceId(), null);

        EtapeItineraire etape = etapeMapper.toEntity(request);
        etape.setSens(sens);
        etape.setAgence(agence);

        return etapeMapper.toResponse(etapeRepository.save(etape));
    }

    @Transactional
    public EtapeItineraireResponse update(Long id, EtapeItineraireRequest request) {
        EtapeItineraire etape = getEtapeOrThrow(id);
        Sens sens = getSensOrThrow(request.sensId());
        Agence agence = getAgenceOrThrow(request.agenceId());
        ensureOrdreIsAvailable(request.sensId(), request.ordre(), id);
        ensureAgenceIsAvailable(request.sensId(), request.agenceId(), id);

        etapeMapper.updateEntityFromRequest(request, etape);
        etape.setSens(sens);
        etape.setAgence(agence);

        return etapeMapper.toResponse(etapeRepository.save(etape));
    }

    @Transactional
    public void delete(Long id) {
        EtapeItineraire etape = getEtapeOrThrow(id);
        etapeRepository.delete(etape);
    }

    private EtapeItineraire getEtapeOrThrow(Long id) {
        return etapeRepository.findById(id)
                .orElseThrow(() -> new ReferentielNotFoundException("EtapeItineraire", id));
    }

    private Sens getSensOrThrow(Long sensId) {
        return sensRepository.findById(sensId)
                .orElseThrow(() -> new ReferentielNotFoundException("Sens", sensId));
    }

    private Agence getAgenceOrThrow(Long agenceId) {
        return agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ReferentielNotFoundException("Agence", agenceId));
    }

    private void ensureOrdreIsAvailable(Long sensId, Integer ordre, Long excludedEtapeId) {
        boolean dejaPris = etapeRepository.findBySensIdOrderByOrdreAsc(sensId).stream()
                .anyMatch(e -> e.getOrdre().equals(ordre) && !e.getId().equals(excludedEtapeId));
        if (dejaPris) {
            throw new ReferentielConflictException(
                    "L'ordre " + ordre + " est déjà utilisé dans ce sens.");
        }
    }

    private void ensureAgenceIsAvailable(Long sensId, Long agenceId, Long excludedEtapeId) {
        boolean dejaPresente = etapeRepository.findBySensIdOrderByOrdreAsc(sensId).stream()
                .anyMatch(e -> e.getAgence().getId().equals(agenceId) && !e.getId().equals(excludedEtapeId));
        if (dejaPresente) {
            throw new ReferentielConflictException(
                    "Cette agence figure déjà dans l'itinéraire de ce sens.");
        }
    }
}