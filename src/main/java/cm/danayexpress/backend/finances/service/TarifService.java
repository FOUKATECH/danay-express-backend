package cm.danayexpress.backend.finances.service;

import cm.danayexpress.backend.finances.dto.TarifRequest;
import cm.danayexpress.backend.finances.dto.TarifResponse;
import cm.danayexpress.backend.finances.entity.Tarif;
import cm.danayexpress.backend.finances.enums.StatutTarif;
import cm.danayexpress.backend.finances.exception.FinancesConflictException;
import cm.danayexpress.backend.finances.exception.FinancesNotFoundException;
import cm.danayexpress.backend.finances.mapper.TarifMapper;
import cm.danayexpress.backend.finances.repository.TarifRepository;
import cm.danayexpress.backend.referentiel.entity.Agence;
import cm.danayexpress.backend.referentiel.exception.ReferentielNotFoundException;
import cm.danayexpress.backend.referentiel.repository.AgenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Logique métier de la grille tarifaire (CDC section 8.6). Empêche
 * deux tarifs ACTIF de se chevaucher pour un même trajet
 * (origine + destination) et type de véhicule.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TarifService {

    private final TarifRepository tarifRepository;
    private final AgenceRepository agenceRepository;
    private final TarifMapper tarifMapper;

    public List<TarifResponse> findAll() {
        return tarifRepository.findAll().stream()
                .map(tarifMapper::toResponse)
                .toList();
    }

    public List<TarifResponse> findByStatut(StatutTarif statut) {
        return tarifRepository.findByStatut(statut).stream()
                .map(tarifMapper::toResponse)
                .toList();
    }

    public TarifResponse findById(Long id) {
        return tarifMapper.toResponse(getTarifOrThrow(id));
    }

    @Transactional
    public TarifResponse create(TarifRequest request) {
        validateOrigineDestinationDistinctes(request.origineId(), request.destinationId());
        Agence origine = getAgenceOrThrow(request.origineId());
        Agence destination = getAgenceOrThrow(request.destinationId());
        ensurePasDeChevauchement(request, null);

        Tarif tarif = tarifMapper.toEntity(request);
        tarif.setOrigine(origine);
        tarif.setDestination(destination);
        tarif.setStatut(request.statut() != null ? request.statut() : StatutTarif.ACTIF);

        return tarifMapper.toResponse(tarifRepository.save(tarif));
    }

    @Transactional
    public TarifResponse update(Long id, TarifRequest request) {
        Tarif tarif = getTarifOrThrow(id);
        validateOrigineDestinationDistinctes(request.origineId(), request.destinationId());
        Agence origine = getAgenceOrThrow(request.origineId());
        Agence destination = getAgenceOrThrow(request.destinationId());
        ensurePasDeChevauchement(request, id);

        tarifMapper.updateEntityFromRequest(request, tarif);
        tarif.setOrigine(origine);
        tarif.setDestination(destination);
        tarif.setStatut(request.statut() != null ? request.statut() : tarif.getStatut());

        return tarifMapper.toResponse(tarifRepository.save(tarif));
    }

    @Transactional
    public void delete(Long id) {
        tarifRepository.delete(getTarifOrThrow(id));
    }

    private Tarif getTarifOrThrow(Long id) {
        return tarifRepository.findById(id)
                .orElseThrow(() -> new FinancesNotFoundException("Tarif", id));
    }

    private Agence getAgenceOrThrow(Long agenceId) {
        return agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ReferentielNotFoundException("Agence", agenceId));
    }

    private void validateOrigineDestinationDistinctes(Long origineId, Long destinationId) {
        if (origineId.equals(destinationId)) {
            throw new FinancesConflictException("L'origine et la destination doivent être différentes.");
        }
    }

    /** Deux périodes [d1,f1] et [d2,f2] (f null = infini) se chevauchent si d1 <= f2 et d2 <= f1. */
    private void ensurePasDeChevauchement(TarifRequest request, Long excludedTarifId) {
        List<Tarif> tarifsExistants = tarifRepository.findByOrigineIdAndDestinationIdAndTypeVehiculeAndStatut(
                request.origineId(), request.destinationId(), request.typeVehicule(), StatutTarif.ACTIF);

        for (Tarif existant : tarifsExistants) {
            if (existant.getId().equals(excludedTarifId)) {
                continue;
            }
            boolean chevauche = !request.dateDebut().isAfter(orMax(existant.getDateFin()))
                    && !existant.getDateDebut().isAfter(orMax(request.dateFin()));
            if (chevauche) {
                throw new FinancesConflictException(
                        "Un tarif actif existe déjà pour ce trajet et ce type de véhicule sur une période qui se chevauche.");
            }
        }
    }

    private LocalDate orMax(LocalDate date) {
        return date != null ? date : LocalDate.MAX;
    }
}