package cm.danayexpress.backend.finances.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.passagers.entity.MouvementTransit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Recette générée par un mouvement de descente (CDC section 8.6) :
 * nombre de passagers du mouvement × tarif applicable. Un mouvement de
 * type DESCENTE donne au plus une Recette (relation 1:1) ; pas de
 * Recette pour les mouvements MONTEE (le trajet n'est facturé qu'une
 * fois complété, à la descente).
 */
@Entity
@Table(name = "recettes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Recette extends AuditableEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mouvement_transit_id", nullable = false, unique = true)
    private MouvementTransit mouvementTransit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tarif_id", nullable = false)
    private Tarif tarif;

    @Column(name = "montant", nullable = false, precision = 12, scale = 2)
    private BigDecimal montant;
}