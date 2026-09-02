package cm.danayexpress.backend.referentiel.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Ligne (corridor) reliant deux agences principales, ex: Yagoua - Garoua
 * (CDC section 6.1). Une ligne possède un ou deux Sens (ALLER/RETOUR,
 * voir RM-09), chacun avec son propre ordre d'agences.
 */
@Entity
@Table(name = "lignes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Ligne extends AuditableEntity {

    @Column(name = "code", nullable = false, length = 20, unique = true)
    private String code;

    @Column(name = "nom", nullable = false, length = 150)
    private String nom;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    @Builder.Default
    private StatutReferentiel statut = StatutReferentiel.ACTIVE;
}