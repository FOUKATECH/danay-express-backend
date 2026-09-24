package cm.danayexpress.backend.finances.controller;

import cm.danayexpress.backend.finances.dto.CalculRecettesResponse;
import cm.danayexpress.backend.finances.dto.RecetteResponse;
import cm.danayexpress.backend.finances.service.RecetteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finances")
@RequiredArgsConstructor
public class RecetteController {

    private final RecetteService recetteService;

    @GetMapping("/recettes")
    public ResponseEntity<List<RecetteResponse>> findAll() {
        return ResponseEntity.ok(recetteService.findAll());
    }

    @GetMapping("/voyages/{voyageId}/recettes")
    public ResponseEntity<List<RecetteResponse>> findByVoyageId(@PathVariable Long voyageId) {
        return ResponseEntity.ok(recetteService.findByVoyageId(voyageId));
    }

    /** Calcule les recettes manquantes pour ce voyage (descentes sans recette, avec un tarif applicable). */
    @PostMapping("/voyages/{voyageId}/calculer-recettes")
    public ResponseEntity<CalculRecettesResponse> calculerRecettes(@PathVariable Long voyageId) {
        return ResponseEntity.ok(recetteService.calculerPourVoyage(voyageId));
    }
}