package cm.danayexpress.backend.referentiel.service;

import cm.danayexpress.backend.referentiel.dto.SensRequest;
import cm.danayexpress.backend.referentiel.dto.SensResponse;
import cm.danayexpress.backend.referentiel.entity.Agence;
import cm.danayexpress.backend.referentiel.entity.Ligne;
import cm.danayexpress.backend.referentiel.entity.Sens;
import cm.danayexpress.backend.referentiel.enums.SensCode;
import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;
import cm.danayexpress.backend.referentiel.exception.ReferentielConflictException;
import cm.danayexpress.backend.referentiel.exception.ReferentielNotFoundException;
import cm.danayexpress.backend.referentiel.mapper.SensMapper;
import cm.danayexpress.backend.referentiel.repository.AgenceRepository;
import cm.danayexpress.backend.referentiel.repository.LigneRepository;
import cm.danayexpress.backend.referentiel.repository.SensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Logique métier du Sens. Fait respecter :
 * - RM-08 : l'agence de départ doit être différente de l'agence d'arrivée.
 * - RM-09 : une ligne ne peut avoir qu'un seul sens ALLER et un seul sens RETOUR.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensService {

    private final SensRepository sensRepository;
    private final LigneRepository ligneRepository;
    private final AgenceRepository agenceRepository;
    private final SensMapper sensMapper;

    public List<SensResponse> findAll() {
        return sensRepository.findAll().stream()
                .map(sensMapper::toResponse)
                .toList();
    }

    public List<SensResponse> findByLigneId(Long ligneId) {
        return sensRepository.findByLigneId(ligneId).stream()
                .map(sensMapper::toResponse)
                .toList();
    }

    public SensResponse findById(Long id) {
        return sensMapper.toResponse(getSensOrThrow(id));
    }

    @Transactional
    public SensResponse create(SensRequest request) {
        validateAgencesDistinctes(request.agenceDepartId(), request.agenceArriveeId());

        Ligne ligne = getLigneOrThrow(request.ligneId());
        ensureSensIsAvailable(request.ligneId(), request.code(), null);
        Agence agenceDepart = getAgenceOrThrow(request.agenceDepartId());
        Agence agenceArrivee = getAgenceOrThrow(request.agenceArriveeId());

        Sens sens = sensMapper.toEntity(request);
        sens.setLigne(ligne);
        sens.setAgenceDepart(agenceDepart);
        sens.setAgenceArrivee(agenceArrivee);
        sens.setStatut(request.statut() != null ? request.statut() : StatutReferentiel.ACTIVE);

        return sensMapper.toResponse(sensRepository.save(sens));
    }

    @Transactional
    public SensResponse update(Long id, SensRequest request) {
        validateAgencesDistinctes(request.agenceDepartId(), request.agenceArriveeId());

        Sens sens = getSensOrThrow(id);
        Ligne ligne = getLigneOrThrow(request.ligneId());
        ensureSensIsAvailable(request.ligneId(), request.code(), id);
        Agence agenceDepart = getAgenceOrThrow(request.agenceDepartId());
        Agence agenceArrivee = getAgenceOrThrow(request.agenceArriveeId());

        sensMapper.updateEntityFromRequest(request, sens);
        sens.setLigne(ligne);
        sens.setAgenceDepart(agenceDepart);
        sens.setAgenceArrivee(agenceArrivee);
        sens.setStatut(request.statut() != null ? request.statut() : sens.getStatut());

        return sensMapper.toResponse(sensRepository.save(sens));
    }

    @Transactional
    public void delete(Long id) {
        Sens sens = getSensOrThrow(id);
        sensRepository.delete(sens);
    }

    private Sens getSensOrThrow(Long id) {
        return sensRepository.findById(id)
                .orElseThrow(() -> new ReferentielNotFoundException("Sens", id));
    }

    private Ligne getLigneOrThrow(Long ligneId) {
        return ligneRepository.findById(ligneId)
                .orElseThrow(() -> new ReferentielNotFoundException("Ligne", ligneId));
    }

    private Agence getAgenceOrThrow(Long agenceId) {
        return agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ReferentielNotFoundException("Agence", agenceId));
    }

    /** RM-08 : une agence ne peut pas être à la fois départ et arrivée d'un même sens. */
    private void validateAgencesDistinctes(Long agenceDepartId, Long agenceArriveeId) {
        if (agenceDepartId.equals(agenceArriveeId)) {
            throw new ReferentielConflictException(
                    "L'agence de départ et l'agence d'arrivée doivent être différentes.");
        }
    }

    /** RM-09 : au plus un sens par (ligne, code ALLER/RETOUR). */
    private void ensureSensIsAvailable(Long ligneId, SensCode code, Long excludedSensId) {
        sensRepository.findByLigneIdAndCode(ligneId, code).ifPresent(existing -> {
            if (!existing.getId().equals(excludedSensId)) {
                throw new ReferentielConflictException(
                        "Un sens " + code + " existe déjà pour cette ligne.");
            }
        });
    }
}