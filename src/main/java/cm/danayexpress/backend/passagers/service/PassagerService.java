package cm.danayexpress.backend.passagers.service;

import cm.danayexpress.backend.exploitation.entity.Escale;
import cm.danayexpress.backend.exploitation.entity.Voyage;
import cm.danayexpress.backend.exploitation.repository.EscaleRepository;
import cm.danayexpress.backend.exploitation.repository.VoyageRepository;
import cm.danayexpress.backend.passagers.dto.EtapeEffectifResponse;
import cm.danayexpress.backend.passagers.dto.EvolutionEffectifsResponse;
import cm.danayexpress.backend.passagers.dto.MouvementTransitResponse;
import cm.danayexpress.backend.passagers.dto.SoldeOrigineResponse;
import cm.danayexpress.backend.passagers.exception.PassagersNotFoundException;
import cm.danayexpress.backend.passagers.mapper.MouvementTransitMapper;
import cm.danayexpress.backend.passagers.repository.MouvementTransitRepository;
import cm.danayexpress.backend.referentiel.repository.AgenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Consultation des données passagers (CDC section 8.5). Ne détient
 * aucune donnée propre : agrège ce qui a déjà été enregistré par les
 * modules Exploitation (Voyage, Escale) et le suivi des mouvements de
 * transit.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PassagerService {

    private final VoyageRepository voyageRepository;
    private final EscaleRepository escaleRepository;
    private final AgenceRepository agenceRepository;
    private final MouvementTransitRepository mouvementTransitRepository;
    private final MouvementTransitMapper mouvementTransitMapper;
    private final SoldeOrigineCalculator soldeOrigineCalculator;

    public List<MouvementTransitResponse> findMouvementsByVoyageId(Long voyageId) {
        ensureVoyageExiste(voyageId);
        return mouvementTransitRepository.findByVoyageIdOrderByCreatedAtAsc(voyageId).stream()
                .map(mouvementTransitMapper::toResponse)
                .toList();
    }

    public List<SoldeOrigineResponse> findSoldesOrigineByVoyageId(Long voyageId) {
        ensureVoyageExiste(voyageId);
        Map<Long, Integer> soldes = soldeOrigineCalculator.calculerSoldes(voyageId);

        return soldes.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> {
                    String nomAgence = agenceRepository.findById(entry.getKey())
                            .map(a -> a.getNom())
                            .orElse("Agence #" + entry.getKey());
                    return new SoldeOrigineResponse(entry.getKey(), nomAgence, entry.getValue());
                })
                .toList();
    }

    public EvolutionEffectifsResponse findEvolutionEffectifs(Long voyageId) {
        Voyage voyage = voyageRepository.findById(voyageId)
                .orElseThrow(() -> new PassagersNotFoundException("Voyage", voyageId));

        List<Escale> escales = escaleRepository.findByVoyageIdOrderByOrdreAsc(voyageId);
        List<EtapeEffectifResponse> etapes = escales.stream()
                .map(e -> new EtapeEffectifResponse(
                        e.getAgence().getNom(),
                        e.getOrdre(),
                        e.getHeureArrivee(),
                        e.getPassagersDescendus(),
                        e.getPassagersMontes(),
                        e.getEffectifABord()
                ))
                .toList();

        return new EvolutionEffectifsResponse(
                voyage.getId(),
                voyage.getStatut(),
                voyage.getAgenceDepart().getNom(),
                voyage.getEffectifPassagersDepart(),
                etapes,
                voyage.getDestinationFinale().getNom()
        );
    }

    private void ensureVoyageExiste(Long voyageId) {
        if (!voyageRepository.existsById(voyageId)) {
            throw new PassagersNotFoundException("Voyage", voyageId);
        }
    }
}