package cm.danayexpress.backend.parcautomobile.controller;

import cm.danayexpress.backend.parcautomobile.dto.VehiculeRequest;
import cm.danayexpress.backend.parcautomobile.dto.VehiculeResponse;
import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;
import cm.danayexpress.backend.parcautomobile.service.VehiculeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parc-automobile/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final VehiculeService vehiculeService;

    /** Sans paramètre : tous les véhicules. Avec ?statut=DISPONIBLE : filtrés par statut. */
    @GetMapping
    public ResponseEntity<List<VehiculeResponse>> findAll(@RequestParam(required = false) StatutVehicule statut) {
        List<VehiculeResponse> result = (statut != null)
                ? vehiculeService.findByStatut(statut)
                : vehiculeService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VehiculeResponse> create(@Valid @RequestBody VehiculeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeResponse> update(@PathVariable Long id, @Valid @RequestBody VehiculeRequest request) {
        return ResponseEntity.ok(vehiculeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehiculeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}