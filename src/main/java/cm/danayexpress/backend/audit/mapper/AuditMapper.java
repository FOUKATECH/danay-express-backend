package cm.danayexpress.backend.audit.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.audit.dto.AuditCreateRequest;
import cm.danayexpress.backend.audit.dto.AuditResponse;
import cm.danayexpress.backend.audit.entity.JournalAudit;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface AuditMapper {

    AuditResponse toResponse(JournalAudit journalAudit);

    JournalAudit toEntity(AuditCreateRequest request);
}
