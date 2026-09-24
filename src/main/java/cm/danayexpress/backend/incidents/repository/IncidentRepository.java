package cm.danayexpress.backend.incidents.repository;

import cm.danayexpress.backend.incidents.entity.Incident;
import cm.danayexpress.backend.incidents.enums.GraviteIncident;
import cm.danayexpress.backend.incidents.enums.StatutIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long>, JpaSpecificationExecutor<Incident> {

    List<Incident> findByVehiculeId(Long vehiculeId);

    List<Incident> findByVoyageId(Long voyageId);

    List<Incident> findByStatut(StatutIncident statut);

    List<Incident> findByGravite(GraviteIncident gravite);

    boolean existsByVehiculeIdAndStatutIn(Long vehiculeId, List<StatutIncident> statuts);
}
