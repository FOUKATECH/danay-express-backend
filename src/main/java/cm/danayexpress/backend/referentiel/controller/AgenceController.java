package cm.danayexpress.backend.referentiel.controller;

import cm.danayexpress.backend.referentiel.dto.AgenceRequest;
import cm.danayexpress.backend.referentiel.dto.AgenceResponse;
import cm.danayexpress.backend.referentiel.service.AgenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/referentiel/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final AgenceService agenceService;

    @GetMapping
    public ResponseEntity<List<AgenceResponse>> findAll() {
        return ResponseEntity.ok(agenceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenceResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(agenceService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AgenceResponse> create(@Valid @RequestBody AgenceRequest request) {
        AgenceResponse created = agenceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenceResponse> update(@PathVariable Long id, @Valid @RequestBody AgenceRequest request) {
        return ResponseEntity.ok(agenceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}