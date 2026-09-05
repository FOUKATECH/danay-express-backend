package cm.danayexpress.backend.referentiel.controller;

import cm.danayexpress.backend.referentiel.dto.VilleRequest;
import cm.danayexpress.backend.referentiel.dto.VilleResponse;
import cm.danayexpress.backend.referentiel.service.VilleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST pour le référentiel des villes.
 * Aucune logique métier ici : cette classe traduit uniquement les
 * requêtes HTTP en appels au VilleService, et le résultat en réponses
 * HTTP avec le bon code de statut.
 */
@RestController
@RequestMapping("/referentiel/villes")
@RequiredArgsConstructor
public class VilleController {

    private final VilleService villeService;

    @GetMapping
    public ResponseEntity<List<VilleResponse>> findAll() {
        return ResponseEntity.ok(villeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VilleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(villeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VilleResponse> create(@Valid @RequestBody VilleRequest request) {
        VilleResponse created = villeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VilleResponse> update(@PathVariable Long id, @Valid @RequestBody VilleRequest request) {
        return ResponseEntity.ok(villeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        villeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}