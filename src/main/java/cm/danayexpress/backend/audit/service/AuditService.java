package cm.danayexpress.backend.audit.service;

import cm.danayexpress.backend.audit.dto.AuditCreateRequest;
import cm.danayexpress.backend.audit.dto.AuditResponse;
import cm.danayexpress.backend.audit.enums.ModuleApplicatif;
import cm.danayexpress.backend.audit.enums.TypeActionAudit;

import java.time.Instant;
import java.util.List;

/**
 * Service de journalisation et de consultation des événements d'audit (CDC section 8.12).
 */
public interface AuditService {

    AuditResponse enregistrerLog(AuditCreateRequest request);

    void enregistrerLogDirect(String nomUtilisateur, TypeActionAudit action, ModuleApplicatif module, String elementId, String description, String ancienneValeur, String nouvelleValeur);

    void enregistrerActionCourante(TypeActionAudit action, ModuleApplicatif module, String elementId, String description, String ancienneValeur, String nouvelleValeur);

    AuditResponse getLogById(Long id);

    List<AuditResponse> listerLogs(String nomUtilisateur, ModuleApplicatif module, TypeActionAudit action, Instant debut, Instant fin);
}
