package cm.danayexpress.backend.ressourcesoperationnelles.controller;

import cm.danayexpress.backend.ressourcesoperationnelles.dto.AffectationRequest;
import cm.danayexpress.backend.ressourcesoperationnelles.dto.AffectationResponse;
import cm.danayexpress.backend.ressourcesoperationnelles.service.AffectationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ressources-operationnelles/affectations")
@RequiredArgsConstructor
public class AffectationController {

    private final AffectationService affectationService;

    /**
     * Sans paramètre : toutes les affectations.
     * ?chauffeurId=X : historique d'un chauffeur. ?vehiculeId=Y : historique d'un véhicule.
     */
    @GetMapping
    public ResponseEntity<List<AffectationResponse>> findAll(
            @RequestParam(required = false) Long chauffeurId,
            @RequestParam(required = false) Long vehiculeId) {

        List<AffectationResponse> result;
        if (chauffeurId != null) {
            result = affectationService.findByChauffeurId(chauffeurId);
        } else if (vehiculeId != null) {
            result = affectationService.findByVehiculeId(vehiculeId);
        } else {
            result = affectationService.findAll();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AffectationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(affectationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AffectationResponse> create(@Valid @RequestBody AffectationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(affectationService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AffectationResponse> update(@PathVariable Long id, @Valid @RequestBody AffectationRequest request) {
        return ResponseEntity.ok(affectationService.update(id, request));
    }

    /** Clôture l'affectation (date de fin = aujourd'hui, statut = TERMINEE). */
    @PutMapping("/{id}/terminer")
    public ResponseEntity<AffectationResponse> terminer(@PathVariable Long id) {
        return ResponseEntity.ok(affectationService.terminer(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        affectationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}