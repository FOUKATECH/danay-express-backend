package cm.danayexpress.backend.referentiel.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.referentiel.enums.SensCode;
import cm.danayexpress.backend.referentiel.enums.StatutReferentiel;
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
 * Sens de circulation sur une ligne (ALLER ou RETOUR).
 * Porte l'agence de départ et l'agence d'arrivée pour ce sens
 * (RM-08 : une agence de départ peut aussi être une destination finale,
 * selon le sens considéré). L'ordre détaillé des agences traversées
 * est porté par EtapeItineraire.
 */
@Entity
@Table(name = "sens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Sens extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ligne_id", nullable = false)
    private Ligne ligne;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, length = 10)
    private SensCode code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agence_depart_id", nullable = false)
    private Agence agenceDepart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agence_arrivee_id", nullable = false)
    private Agence agenceArrivee;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    @Builder.Default
    private StatutReferentiel statut = StatutReferentiel.ACTIVE;
}