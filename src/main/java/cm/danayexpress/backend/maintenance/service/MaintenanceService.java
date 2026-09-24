package cm.danayexpress.backend.maintenance.service;

import cm.danayexpress.backend.maintenance.dto.CloturerMaintenanceRequest;
import cm.danayexpress.backend.maintenance.dto.MaintenanceCreateRequest;
import cm.danayexpress.backend.maintenance.dto.MaintenanceResponse;
import cm.danayexpress.backend.maintenance.dto.MaintenanceUpdateRequest;
import cm.danayexpress.backend.maintenance.enums.StatutInterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.TypeInterventionMaintenance;

import java.util.List;

/**
 * Service pour la gestion de la maintenance du parc automobile (CDC section 8.8).
 */
public interface MaintenanceService {

    MaintenanceResponse creerMaintenance(MaintenanceCreateRequest request);

    MaintenanceResponse modifierMaintenance(Long id, MaintenanceUpdateRequest request);

    MaintenanceResponse getMaintenanceById(Long id);

    List<MaintenanceResponse> listerMaintenances(Long vehiculeId, StatutInterventionMaintenance statut, TypeInterventionMaintenance typeIntervention);

    MaintenanceResponse cloturerMaintenance(Long id, CloturerMaintenanceRequest request);

    MaintenanceResponse annulerMaintenance(Long id, String motif);
}
