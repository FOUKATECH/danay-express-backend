package cm.danayexpress.backend.incidents.repository;

import cm.danayexpress.backend.incidents.entity.InterventionSecours;
import cm.danayexpress.backend.incidents.enums.StatutInterventionSecours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionSecoursRepository extends JpaRepository<InterventionSecours, Long> {

    List<InterventionSecours> findByIncidentId(Long incidentId);

    List<InterventionSecours> findByVehiculeSecoursId(Long vehiculeSecoursId);

    List<InterventionSecours> findByStatut(StatutInterventionSecours statut);

    boolean existsByVehiculeSecoursIdAndStatutIn(Long vehiculeSecoursId, List<StatutInterventionSecours> statuts);
}
