package cm.danayexpress.backend.audit.repository;

import cm.danayexpress.backend.audit.entity.JournalAudit;
import cm.danayexpress.backend.audit.enums.ModuleApplicatif;
import cm.danayexpress.backend.audit.enums.TypeActionAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface JournalAuditRepository extends JpaRepository<JournalAudit, Long>, JpaSpecificationExecutor<JournalAudit> {

    List<JournalAudit> findByNomUtilisateur(String nomUtilisateur);

    List<JournalAudit> findByModule(ModuleApplicatif module);

    List<JournalAudit> findByAction(TypeActionAudit action);

    List<JournalAudit> findByCreatedAtBetween(Instant debut, Instant fin);
}
