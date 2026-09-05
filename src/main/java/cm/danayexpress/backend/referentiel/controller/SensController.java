package cm.danayexpress.backend.referentiel.controller;

import cm.danayexpress.backend.referentiel.dto.SensRequest;
import cm.danayexpress.backend.referentiel.dto.SensResponse;
import cm.danayexpress.backend.referentiel.service.SensService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/referentiel/sens")
@RequiredArgsConstructor
public class SensController {

    private final SensService sensService;

    /** Sans paramètre : tous les sens. Avec ?ligneId=X : seulement ceux de cette ligne. */
    @GetMapping
    public ResponseEntity<List<SensResponse>> findAll(@RequestParam(required = false) Long ligneId) {
        List<SensResponse> result = (ligneId != null)
                ? sensService.findByLigneId(ligneId)
                : sensService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(sensService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SensResponse> create(@Valid @RequestBody SensRequest request) {
        SensResponse created = sensService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensResponse> update(@PathVariable Long id, @Valid @RequestBody SensRequest request) {
        return ResponseEntity.ok(sensService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sensService.delete(id);
        return ResponseEntity.noContent().build();
    }
}