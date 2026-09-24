package cm.danayexpress.backend.maintenance.controller;

import cm.danayexpress.backend.maintenance.dto.CloturerMaintenanceRequest;
import cm.danayexpress.backend.maintenance.dto.MaintenanceCreateRequest;
import cm.danayexpress.backend.maintenance.dto.MaintenanceResponse;
import cm.danayexpress.backend.maintenance.dto.MaintenanceUpdateRequest;
import cm.danayexpress.backend.maintenance.enums.StatutInterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.TypeInterventionMaintenance;
import cm.danayexpress.backend.maintenance.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceResponse> creerMaintenance(@Valid @RequestBody MaintenanceCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.creerMaintenance(request));
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponse>> listerMaintenances(
            @RequestParam(required = false) Long vehiculeId,
            @RequestParam(required = false) StatutInterventionMaintenance statut,
            @RequestParam(required = false) TypeInterventionMaintenance typeIntervention) {
        return ResponseEntity.ok(maintenanceService.listerMaintenances(vehiculeId, statut, typeIntervention));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponse> getMaintenanceById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceService.getMaintenanceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponse> modifierMaintenance(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceUpdateRequest request) {
        return ResponseEntity.ok(maintenanceService.modifierMaintenance(id, request));
    }

    @PostMapping("/{id}/cloturer")
    public ResponseEntity<MaintenanceResponse> cloturerMaintenance(
            @PathVariable Long id,
            @Valid @RequestBody(required = false) CloturerMaintenanceRequest request) {
        CloturerMaintenanceRequest body = request != null ? request : new CloturerMaintenanceRequest(null, null, null);
        return ResponseEntity.ok(maintenanceService.cloturerMaintenance(id, body));
    }

    @PostMapping("/{id}/annuler")
    public ResponseEntity<MaintenanceResponse> annulerMaintenance(
            @PathVariable Long id,
            @RequestParam(required = false) String motif) {
        return ResponseEntity.ok(maintenanceService.annulerMaintenance(id, motif));
    }
}
