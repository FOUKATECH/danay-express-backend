package cm.danayexpress.backend.incidents.controller;

import cm.danayexpress.backend.incidents.dto.AffecterSecoursRequest;
import cm.danayexpress.backend.incidents.dto.ChangerStatutInterventionRequest;
import cm.danayexpress.backend.incidents.dto.IncidentCreateRequest;
import cm.danayexpress.backend.incidents.dto.IncidentResponse;
import cm.danayexpress.backend.incidents.dto.IncidentUpdateRequest;
import cm.danayexpress.backend.incidents.dto.InterventionSecoursResponse;
import cm.danayexpress.backend.incidents.enums.GraviteIncident;
import cm.danayexpress.backend.incidents.enums.StatutIncident;
import cm.danayexpress.backend.incidents.service.IncidentService;
import cm.danayexpress.backend.parcautomobile.dto.VehiculeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    @PostMapping
    public ResponseEntity<IncidentResponse> declarerIncident(@Valid @RequestBody IncidentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(incidentService.declarerIncident(request));
    }

    @GetMapping
    public ResponseEntity<List<IncidentResponse>> listerIncidents(
            @RequestParam(required = false) StatutIncident statut,
            @RequestParam(required = false) GraviteIncident gravite,
            @RequestParam(required = false) Long vehiculeId,
            @RequestParam(required = false) Long voyageId,
            @RequestParam(required = false) Long agenceId) {
        return ResponseEntity.ok(incidentService.listerIncidents(statut, gravite, vehiculeId, voyageId, agenceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponse> getIncidentById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.getIncidentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentResponse> modifierIncident(
            @PathVariable Long id,
            @Valid @RequestBody IncidentUpdateRequest request) {
        return ResponseEntity.ok(incidentService.modifierIncident(id, request));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<IncidentResponse> modifierStatutIncident(
            @PathVariable Long id,
            @RequestParam StatutIncident statut) {
        return ResponseEntity.ok(incidentService.modifierStatutIncident(id, statut));
    }

    @GetMapping("/secours/vehicules-disponibles")
    public ResponseEntity<List<VehiculeResponse>> rechercherVehiculesSecoursDisponibles(
            @RequestParam(required = false) Long agenceId) {
        return ResponseEntity.ok(incidentService.rechercherVehiculesSecoursDisponibles(agenceId));
    }

    @PostMapping("/{id}/secours")
    public ResponseEntity<InterventionSecoursResponse> affecterVehiculeSecours(
            @PathVariable Long id,
            @Valid @RequestBody AffecterSecoursRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(incidentService.affecterVehiculeSecours(id, request));
    }

    @GetMapping("/{id}/interventions")
    public ResponseEntity<List<InterventionSecoursResponse>> getInterventionsParIncident(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.getInterventionsParIncident(id));
    }

    @PatchMapping("/interventions/{interventionId}/statut")
    public ResponseEntity<InterventionSecoursResponse> changerStatutIntervention(
            @PathVariable Long interventionId,
            @Valid @RequestBody ChangerStatutInterventionRequest request) {
        return ResponseEntity.ok(incidentService.changerStatutIntervention(interventionId, request));
    }

    @PostMapping("/{id}/cloturer")
    public ResponseEntity<IncidentResponse> cloturerIncident(
            @PathVariable Long id,
            @RequestParam(required = false) String observations) {
        return ResponseEntity.ok(incidentService.cloturerIncident(id, observations));
    }
}
