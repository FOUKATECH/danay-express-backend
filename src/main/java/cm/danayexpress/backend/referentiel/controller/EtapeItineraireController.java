package cm.danayexpress.backend.referentiel.controller;

import cm.danayexpress.backend.referentiel.dto.EtapeItineraireRequest;
import cm.danayexpress.backend.referentiel.dto.EtapeItineraireResponse;
import cm.danayexpress.backend.referentiel.service.EtapeItineraireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/referentiel/etapes-itineraire")
@RequiredArgsConstructor
public class EtapeItineraireController {

    private final EtapeItineraireService etapeService;

    /** Sans paramètre : toutes les étapes. Avec ?sensId=X : itinéraire complet de ce sens, ordonné. */
    @GetMapping
    public ResponseEntity<List<EtapeItineraireResponse>> findAll(@RequestParam(required = false) Long sensId) {
        List<EtapeItineraireResponse> result = (sensId != null)
                ? etapeService.findBySensId(sensId)
                : etapeService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtapeItineraireResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(etapeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EtapeItineraireResponse> create(@Valid @RequestBody EtapeItineraireRequest request) {
        EtapeItineraireResponse created = etapeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtapeItineraireResponse> update(@PathVariable Long id, @Valid @RequestBody EtapeItineraireRequest request) {
        return ResponseEntity.ok(etapeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etapeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}