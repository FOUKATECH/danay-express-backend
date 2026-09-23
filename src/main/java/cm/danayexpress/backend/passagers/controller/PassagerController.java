package cm.danayexpress.backend.passagers.controller;

import cm.danayexpress.backend.passagers.dto.EvolutionEffectifsResponse;
import cm.danayexpress.backend.passagers.dto.MouvementTransitResponse;
import cm.danayexpress.backend.passagers.dto.SoldeOrigineResponse;
import cm.danayexpress.backend.passagers.service.PassagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/passagers/voyages/{voyageId}")
@RequiredArgsConstructor
public class PassagerController {

    private final PassagerService passagerService;

    /** Journal détaillé de tous les mouvements (montées/descentes) du voyage, avec leur agence d'origine. */
    @GetMapping("/mouvements")
    public ResponseEntity<List<MouvementTransitResponse>> findMouvements(@PathVariable Long voyageId) {
        return ResponseEntity.ok(passagerService.findMouvementsByVoyageId(voyageId));
    }

    /** Combien de passagers sont encore à bord, par agence d'origine — utile avant d'enregistrer une descente. */
    @GetMapping("/soldes-origine")
    public ResponseEntity<List<SoldeOrigineResponse>> findSoldesOrigine(@PathVariable Long voyageId) {
        return ResponseEntity.ok(passagerService.findSoldesOrigineByVoyageId(voyageId));
    }

    /** Vue d'ensemble : effectif au départ, puis à chaque escale, dans l'ordre du trajet. */
    @GetMapping("/evolution-effectifs")
    public ResponseEntity<EvolutionEffectifsResponse> findEvolutionEffectifs(@PathVariable Long voyageId) {
        return ResponseEntity.ok(passagerService.findEvolutionEffectifs(voyageId));
    }
}