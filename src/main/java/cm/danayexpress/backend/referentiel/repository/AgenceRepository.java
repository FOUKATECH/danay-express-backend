package cm.danayexpress.backend.referentiel.repository;

import cm.danayexpress.backend.referentiel.entity.Agence;
import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgenceRepository extends JpaRepository<Agence, Long> {

    Optional<Agence> findByCode(String code);

    List<Agence> findByStatut(StatutReferentiel statut);

    List<Agence> findByVilleId(Long villeId);
}