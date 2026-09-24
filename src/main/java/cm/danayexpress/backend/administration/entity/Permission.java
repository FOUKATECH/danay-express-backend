package cm.danayexpress.backend.administration.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Permission fine d'accès aux fonctionnalités du système (CDC section 8.13 & 14).
 */
@Entity
@Table(name = "permissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Permission extends AuditableEntity {

    @Column(name = "code", nullable = false, length = 100, unique = true)
    private String code;

    @Column(name = "libelle", nullable = false, length = 150)
    private String libelle;

    @Column(name = "module", nullable = false, length = 50)
    private String module;
}
