package cm.danayexpress.backend.audit.controller;

import cm.danayexpress.backend.audit.dto.AuditCreateRequest;
import cm.danayexpress.backend.audit.dto.AuditResponse;
import cm.danayexpress.backend.audit.enums.ModuleApplicatif;
import cm.danayexpress.backend.audit.enums.TypeActionAudit;
import cm.danayexpress.backend.audit.service.AuditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @PostMapping
    public ResponseEntity<AuditResponse> enregistrerLog(@Valid @RequestBody AuditCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auditService.enregistrerLog(request));
    }

    @GetMapping
    public ResponseEntity<List<AuditResponse>> listerLogs(
            @RequestParam(required = false) String nomUtilisateur,
            @RequestParam(required = false) ModuleApplicatif module,
            @RequestParam(required = false) TypeActionAudit action,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fin) {
        return ResponseEntity.ok(auditService.listerLogs(nomUtilisateur, module, action, debut, fin));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResponse> getLogById(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.getLogById(id));
    }

    @GetMapping("/modules")
    public ResponseEntity<List<ModuleApplicatif>> listerModules() {
        return ResponseEntity.ok(Arrays.asList(ModuleApplicatif.values()));
    }

    @GetMapping("/actions")
    public ResponseEntity<List<TypeActionAudit>> listerActions() {
        return ResponseEntity.ok(Arrays.asList(TypeActionAudit.values()));
    }
}
