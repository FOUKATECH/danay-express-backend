package cm.danayexpress.backend.finances.service;

import cm.danayexpress.backend.finances.dto.CalculRecettesResponse;
import cm.danayexpress.backend.finances.dto.RecetteResponse;
import cm.danayexpress.backend.finances.entity.Recette;
import cm.danayexpress.backend.finances.entity.Tarif;
import cm.danayexpress.backend.finances.mapper.RecetteMapper;
import cm.danayexpress.backend.finances.repository.RecetteRepository;
import cm.danayexpress.backend.finances.repository.TarifRepository;
import cm.danayexpress.backend.passagers.entity.MouvementTransit;
import cm.danayexpress.backend.passagers.enums.TypeMouvement;
import cm.danayexpress.backend.passagers.repository.MouvementTransitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Calcul des recettes (CDC section 8.6) : nombre de passagers × tarif
 * applicable, pour chaque mouvement de type DESCENTE. Déclenché
 * explicitement via {@link #calculerPourVoyage(Long)} plutôt
 * qu'automatiquement depuis le module Exploitation, pour éviter une
 * dépendance circulaire entre modules (Exploitation -> Finances ->
 * Passagers -> Exploitation).
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecetteService {

    private final RecetteRepository recetteRepository;
    private final TarifRepository tarifRepository;
    private final MouvementTransitRepository mouvementTransitRepository;
    private final RecetteMapper recetteMapper;

    public List<RecetteResponse> findByVoyageId(Long voyageId) {
        return recetteRepository.findByMouvementTransit_Voyage_Id(voyageId).stream()
                .map(recetteMapper::toResponse)
                .toList();
    }

    public List<RecetteResponse> findAll() {
        return recetteRepository.findAll().stream()
                .map(recetteMapper::toResponse)
                .toList();
    }

    /**
     * Calcule (ou recalcule) les recettes de tous les mouvements DESCENTE
     * d'un voyage qui n'en ont pas encore. Best-effort : un mouvement
     * sans tarif applicable est simplement compté à part, sans bloquer
     * les autres.
     */
    @Transactional
    public CalculRecettesResponse calculerPourVoyage(Long voyageId) {
        List<MouvementTransit> mouvements = mouvementTransitRepository.findByVoyageIdOrderByCreatedAtAsc(voyageId);

        int creees = 0;
        int dejaCalculees = 0;
        int sansTarif = 0;

        for (MouvementTransit mouvement : mouvements) {
            if (mouvement.getTypeMouvement() != TypeMouvement.DESCENTE) {
                continue;
            }
            if (recetteRepository.existsByMouvementTransitId(mouvement.getId())) {
                dejaCalculees++;
                continue;
            }
            Optional<Recette> recette = calculerPourMouvement(mouvement);
            if (recette.isPresent()) {
                creees++;
            } else {
                sansTarif++;
            }
        }

        return new CalculRecettesResponse(voyageId, creees, dejaCalculees, sansTarif);
    }

    /**
     * Calcule la recette d'un mouvement DESCENTE précis. Ne fait rien
     * (retourne Optional.empty()) si ce n'est pas une descente, si une
     * recette existe déjà, ou si aucun tarif actif ne correspond.
     */
    @Transactional
    public Optional<Recette> calculerPourMouvement(MouvementTransit mouvement) {
        if (mouvement.getTypeMouvement() != TypeMouvement.DESCENTE) {
            return Optional.empty();
        }
        Optional<Recette> existante = recetteRepository.findByMouvementTransitId(mouvement.getId());
        if (existante.isPresent()) {
            return existante;
        }

        String typeVehicule = mouvement.getVoyage().getVehicule().getType();
        Optional<Tarif> tarifApplicable = tarifRepository.findApplicable(
                mouvement.getAgenceOrigine().getId(),
                mouvement.getAgenceMouvement().getId(),
                typeVehicule,
                mouvement.getVoyage().getDateVoyage());

        if (tarifApplicable.isEmpty()) {
            return Optional.empty();
        }

        Tarif tarif = tarifApplicable.get();
        BigDecimal montant = tarif.getMontant().multiply(BigDecimal.valueOf(mouvement.getNombrePassagers()));

        Recette recette = Recette.builder()
                .mouvementTransit(mouvement)
                .tarif(tarif)
                .montant(montant)
                .build();

        return Optional.of(recetteRepository.save(recette));
    }
}