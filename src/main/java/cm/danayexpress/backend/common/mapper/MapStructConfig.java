package cm.danayexpress.backend.common.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * Configuration MapStruct partagée par tous les mappers de l'application.
 *
 * <p>La politique {@code IGNORE} sur les propriétés cibles non mappées est
 * volontaire : les champs {@code id}, {@code createdAt} et {@code updatedAt}
 * (hérités de {@code AuditableEntity}) sont gérés par JPA/Hibernate et
 * l'infrastructure d'audit, pas par la couche de mapping DTO→entité.
 * De même, les champs de statut métier (ex: {@code statut}, {@code heureDepartReelle})
 * sont positionnés par la couche service, jamais depuis un DTO de création.</p>
 */
@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MapStructConfig {
}
