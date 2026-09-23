package cm.danayexpress.backend.passagers.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.exploitation.entity.Escale;
import cm.danayexpress.backend.exploitation.entity.Voyage;
import cm.danayexpress.backend.passagers.enums.TypeMouvement;
import cm.danayexpress.backend.referentiel.entity.Agence;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Mouvement atomique de passagers pour un voyage (CDC section 8.5).
 * Conserve l'agence d'origine de chaque mouvement (règle métier de la
 * section 8.5), indispensable pour attribuer correctement la recette
 * à la bonne agence au module 6.
 */
@Entity
@Table(name = "mouvements_transit")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class MouvementTransit extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "voyage_id", nullable = false)
    private Voyage voyage;

    /** Null si le mouvement a eu lieu au départ réel ou à l'arrivée finale (pas une escale intermédiaire). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escale_id")
    private Escale escale;

    /** Agence où les passagers concernés sont montés à l'origine. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agence_origine_id", nullable = false)
    private Agence agenceOrigine;

    /** Agence où ce mouvement physique a lieu (= agenceOrigine pour une MONTEE). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agence_mouvement_id", nullable = false)
    private Agence agenceMouvement;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_mouvement", nullable = false, length = 20)
    private TypeMouvement typeMouvement;

    @Column(name = "nombre_passagers", nullable = false)
    private Integer nombrePassagers;
}