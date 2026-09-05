package cm.danayexpress.backend.referentiel.controller;

import cm.danayexpress.backend.referentiel.dto.LigneRequest;
import cm.danayexpress.backend.referentiel.dto.LigneResponse;
import cm.danayexpress.backend.referentiel.service.LigneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/referentiel/lignes")
@RequiredArgsConstructor
public class LigneController {

    private final LigneService ligneService;

    @GetMapping
    public ResponseEntity<List<LigneResponse>> findAll() {
        return ResponseEntity.ok(ligneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ligneService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LigneResponse> create(@Valid @RequestBody LigneRequest request) {
        LigneResponse created = ligneService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneResponse> update(@PathVariable Long id, @Valid @RequestBody LigneRequest request) {
        return ResponseEntity.ok(ligneService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ligneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}