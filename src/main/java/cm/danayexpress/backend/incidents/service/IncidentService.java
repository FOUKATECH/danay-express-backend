package cm.danayexpress.backend.incidents.service;

import cm.danayexpress.backend.incidents.dto.AffecterSecoursRequest;
import cm.danayexpress.backend.incidents.dto.ChangerStatutInterventionRequest;
import cm.danayexpress.backend.incidents.dto.IncidentCreateRequest;
import cm.danayexpress.backend.incidents.dto.IncidentResponse;
import cm.danayexpress.backend.incidents.dto.IncidentUpdateRequest;
import cm.danayexpress.backend.incidents.dto.InterventionSecoursResponse;
import cm.danayexpress.backend.incidents.enums.GraviteIncident;
import cm.danayexpress.backend.incidents.enums.StatutIncident;
import cm.danayexpress.backend.parcautomobile.dto.VehiculeResponse;

import java.util.List;

/**
 * Service pour la gestion des incidents et véhicules de secours (CDC section 8.7).
 */
public interface IncidentService {

    IncidentResponse declarerIncident(IncidentCreateRequest request);

    IncidentResponse modifierIncident(Long id, IncidentUpdateRequest request);

    IncidentResponse modifierStatutIncident(Long id, StatutIncident statut);

    IncidentResponse getIncidentById(Long id);

    List<IncidentResponse> listerIncidents(StatutIncident statut, GraviteIncident gravite, Long vehiculeId, Long voyageId, Long agenceId);

    List<VehiculeResponse> rechercherVehiculesSecoursDisponibles(Long agenceId);

    InterventionSecoursResponse affecterVehiculeSecours(Long incidentId, AffecterSecoursRequest request);

    List<InterventionSecoursResponse> getInterventionsParIncident(Long incidentId);

    InterventionSecoursResponse changerStatutIntervention(Long interventionId, ChangerStatutInterventionRequest request);

    IncidentResponse cloturerIncident(Long incidentId, String observations);
}
