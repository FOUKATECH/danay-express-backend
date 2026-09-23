package cm.danayexpress.backend.passagers.service;

import cm.danayexpress.backend.passagers.entity.MouvementTransit;
import cm.danayexpress.backend.passagers.enums.TypeMouvement;
import cm.danayexpress.backend.passagers.repository.MouvementTransitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Calcule, pour un voyage donné, combien de passagers sont encore à
 * bord pour chaque agence d'origine (montées - descentes déjà
 * enregistrées). Utilisé par EscaleService et VoyageService pour
 * valider qu'une répartition de descente ne dépasse pas ce qui est
 * réellement disponible par origine.
 */
@Component
@RequiredArgsConstructor
public class SoldeOrigineCalculator {

    private final MouvementTransitRepository mouvementTransitRepository;

    public Map<Long, Integer> calculerSoldes(Long voyageId) {
        Map<Long, Integer> soldes = new HashMap<>();

        for (MouvementTransit mouvement : mouvementTransitRepository.findByVoyageIdOrderByCreatedAtAsc(voyageId)) {
            Long origineId = mouvement.getAgenceOrigine().getId();
            int variation = mouvement.getTypeMouvement() == TypeMouvement.MONTEE
                    ? mouvement.getNombrePassagers()
                    : -mouvement.getNombrePassagers();
            soldes.merge(origineId, variation, Integer::sum);
        }

        return soldes;
    }

    public int getSolde(Long voyageId, Long agenceOrigineId) {
        return calculerSoldes(voyageId).getOrDefault(agenceOrigineId, 0);
    }
}