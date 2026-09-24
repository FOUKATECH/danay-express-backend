package cm.danayexpress.backend.finances.repository;

import cm.danayexpress.backend.finances.entity.Recette;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecetteRepository extends JpaRepository<Recette, Long> {

    Optional<Recette> findByMouvementTransitId(Long mouvementTransitId);

    boolean existsByMouvementTransitId(Long mouvementTransitId);

    /** Toutes les recettes rattachées à un voyage, via son mouvement de transit. */
    List<Recette> findByMouvementTransit_Voyage_Id(Long voyageId);
}