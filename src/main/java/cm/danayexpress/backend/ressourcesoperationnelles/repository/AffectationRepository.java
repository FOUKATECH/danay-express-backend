package cm.danayexpress.backend.ressourcesoperationnelles.repository;

import cm.danayexpress.backend.ressourcesoperationnelles.entity.Affectation;
import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutAffectation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AffectationRepository extends JpaRepository<Affectation, Long> {

    List<Affectation> findByChauffeurIdOrderByDateDebutDesc(Long chauffeurId);

    List<Affectation> findByVehiculeIdOrderByDateDebutDesc(Long vehiculeId);

    Optional<Affectation> findByChauffeurIdAndStatut(Long chauffeurId, StatutAffectation statut);
}