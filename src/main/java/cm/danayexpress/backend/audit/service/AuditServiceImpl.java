package cm.danayexpress.backend.audit.service;

import cm.danayexpress.backend.audit.dto.AuditCreateRequest;
import cm.danayexpress.backend.audit.dto.AuditResponse;
import cm.danayexpress.backend.audit.entity.JournalAudit;
import cm.danayexpress.backend.audit.enums.ModuleApplicatif;
import cm.danayexpress.backend.audit.enums.TypeActionAudit;
import cm.danayexpress.backend.audit.mapper.AuditMapper;
import cm.danayexpress.backend.audit.repository.JournalAuditRepository;
import cm.danayexpress.backend.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuditServiceImpl implements AuditService {

    private final JournalAuditRepository journalAuditRepository;
    private final AuditMapper auditMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AuditResponse enregistrerLog(AuditCreateRequest request) {
        String username = request.nomUtilisateur();
        if (username == null || username.isBlank()) {
            username = getCurrentUsername();
        }

        JournalAudit journal = auditMapper.toEntity(request);
        journal.setNomUtilisateur(username);
        if (journal.getCreatedAt() == null) {
            journal.setCreatedAt(Instant.now());
        }

        JournalAudit saved = journalAuditRepository.save(journal);
        log.debug("Journal d'audit enregistré ID={} par user={}", saved.getId(), username);

        return auditMapper.toResponse(saved);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void enregistrerLogDirect(String nomUtilisateur, TypeActionAudit action, ModuleApplicatif module, String elementId, String description, String ancienneValeur, String nouvelleValeur) {
        JournalAudit journal = JournalAudit.builder()
                .nomUtilisateur(nomUtilisateur != null ? nomUtilisateur : getCurrentUsername())
                .action(action)
                .module(module)
                .elementId(elementId)
                .description(description)
                .ancienneValeur(ancienneValeur)
                .nouvelleValeur(nouvelleValeur)
                .createdAt(Instant.now())
                .build();

        journalAuditRepository.save(journal);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void enregistrerActionCourante(TypeActionAudit action, ModuleApplicatif module, String elementId, String description, String ancienneValeur, String nouvelleValeur) {
        enregistrerLogDirect(getCurrentUsername(), action, module, elementId, description, ancienneValeur, nouvelleValeur);
    }

    @Override
    public AuditResponse getLogById(Long id) {
        JournalAudit journal = journalAuditRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Enregistrement d'audit introuvable avec l'id " + id, HttpStatus.NOT_FOUND));

        return auditMapper.toResponse(journal);
    }

    @Override
    public List<AuditResponse> listerLogs(String nomUtilisateur, ModuleApplicatif module, TypeActionAudit action, Instant debut, Instant fin) {
        List<JournalAudit> logs = journalAuditRepository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();
            if (nomUtilisateur != null && !nomUtilisateur.isBlank()) {
                predicates.getExpressions().add(cb.equal(root.get("nomUtilisateur"), nomUtilisateur));
            }
            if (module != null) {
                predicates.getExpressions().add(cb.equal(root.get("module"), module));
            }
            if (action != null) {
                predicates.getExpressions().add(cb.equal(root.get("action"), action));
            }
            if (debut != null) {
                predicates.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createdAt"), debut));
            }
            if (fin != null) {
                predicates.getExpressions().add(cb.lessThanOrEqualTo(root.get("createdAt"), fin));
            }
            return predicates;
        });

        return logs.stream()
                .map(auditMapper::toResponse)
                .toList();
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName();
        }
        return "SYSTEM";
    }
}
