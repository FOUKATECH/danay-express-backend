package cm.danayexpress.backend.maintenance.repository;

import cm.danayexpress.backend.maintenance.entity.InterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.StatutInterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.TypeInterventionMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionMaintenanceRepository extends JpaRepository<InterventionMaintenance, Long>, JpaSpecificationExecutor<InterventionMaintenance> {

    List<InterventionMaintenance> findByVehiculeId(Long vehiculeId);

    List<InterventionMaintenance> findByIncidentId(Long incidentId);

    List<InterventionMaintenance> findByStatut(StatutInterventionMaintenance statut);

    List<InterventionMaintenance> findByTypeIntervention(TypeInterventionMaintenance typeIntervention);

    boolean existsByVehiculeIdAndStatutIn(Long vehiculeId, List<StatutInterventionMaintenance> statuts);
}
