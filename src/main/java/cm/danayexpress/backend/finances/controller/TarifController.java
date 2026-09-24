package cm.danayexpress.backend.finances.controller;

import cm.danayexpress.backend.finances.dto.TarifRequest;
import cm.danayexpress.backend.finances.dto.TarifResponse;
import cm.danayexpress.backend.finances.enums.StatutTarif;
import cm.danayexpress.backend.finances.service.TarifService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finances/tarifs")
@RequiredArgsConstructor
public class TarifController {

    private final TarifService tarifService;

    @GetMapping
    public ResponseEntity<List<TarifResponse>> findAll(@RequestParam(required = false) StatutTarif statut) {
        List<TarifResponse> result = (statut != null) ? tarifService.findByStatut(statut) : tarifService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tarifService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TarifResponse> create(@Valid @RequestBody TarifRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarifService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifResponse> update(@PathVariable Long id, @Valid @RequestBody TarifRequest request) {
        return ResponseEntity.ok(tarifService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tarifService.delete(id);
        return ResponseEntity.noContent().build();
    }
}