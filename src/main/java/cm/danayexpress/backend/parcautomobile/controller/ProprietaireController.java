package cm.danayexpress.backend.parcautomobile.controller;

import cm.danayexpress.backend.parcautomobile.dto.ProprietaireRequest;
import cm.danayexpress.backend.parcautomobile.dto.ProprietaireResponse;
import cm.danayexpress.backend.parcautomobile.service.ProprietaireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parc-automobile/proprietaires")
@RequiredArgsConstructor
public class ProprietaireController {

    private final ProprietaireService proprietaireService;

    @GetMapping
    public ResponseEntity<List<ProprietaireResponse>> findAll() {
        return ResponseEntity.ok(proprietaireService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProprietaireResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(proprietaireService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProprietaireResponse> create(@Valid @RequestBody ProprietaireRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proprietaireService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProprietaireResponse> update(@PathVariable Long id, @Valid @RequestBody ProprietaireRequest request) {
        return ResponseEntity.ok(proprietaireService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proprietaireService.delete(id);
        return ResponseEntity.noContent().build();
    }
}