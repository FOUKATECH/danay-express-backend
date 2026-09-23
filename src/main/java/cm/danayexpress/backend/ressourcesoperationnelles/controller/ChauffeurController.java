package cm.danayexpress.backend.ressourcesoperationnelles.controller;

import cm.danayexpress.backend.ressourcesoperationnelles.dto.ChauffeurRequest;
import cm.danayexpress.backend.ressourcesoperationnelles.dto.ChauffeurResponse;
import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutChauffeur;
import cm.danayexpress.backend.ressourcesoperationnelles.service.ChauffeurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ressources-operationnelles/chauffeurs")
@RequiredArgsConstructor
public class ChauffeurController {

    private final ChauffeurService chauffeurService;

    @GetMapping
    public ResponseEntity<List<ChauffeurResponse>> findAll(@RequestParam(required = false) StatutChauffeur statut) {
        List<ChauffeurResponse> result = (statut != null)
                ? chauffeurService.findByStatut(statut)
                : chauffeurService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChauffeurResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(chauffeurService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ChauffeurResponse> create(@Valid @RequestBody ChauffeurRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chauffeurService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChauffeurResponse> update(@PathVariable Long id, @Valid @RequestBody ChauffeurRequest request) {
        return ResponseEntity.ok(chauffeurService.update(id, request));
    }

    /** Raccourci pratique correspondant au "activer/désactiver un chauffeur" du CDC. */
    @PutMapping("/{id}/activer")
    public ResponseEntity<ChauffeurResponse> activer(@PathVariable Long id) {
        return ResponseEntity.ok(chauffeurService.activer(id));
    }

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<ChauffeurResponse> desactiver(@PathVariable Long id) {
        return ResponseEntity.ok(chauffeurService.desactiver(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chauffeurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}