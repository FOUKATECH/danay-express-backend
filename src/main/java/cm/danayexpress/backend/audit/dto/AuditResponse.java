package cm.danayexpress.backend.audit.dto;

import cm.danayexpress.backend.audit.enums.ModuleApplicatif;
import cm.danayexpress.backend.audit.enums.TypeActionAudit;

import java.time.Instant;

public record AuditResponse(
        Long id,
        String nomUtilisateur,
        TypeActionAudit action,
        ModuleApplicatif module,
        String elementId,
        String description,
        String ancienneValeur,
        String nouvelleValeur,
        String adresseIp,
        Instant createdAt
) {
}
