package cm.danayexpress.backend.referentiel.repository;

import cm.danayexpress.backend.referentiel.entity.EtapeItineraire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtapeItineraireRepository extends JpaRepository<EtapeItineraire, Long> {

    /** Retourne les étapes d'un sens, triées par ordre — pratique pour reconstruire l'itinéraire complet. */
    List<EtapeItineraire> findBySensIdOrderByOrdreAsc(Long sensId);
}