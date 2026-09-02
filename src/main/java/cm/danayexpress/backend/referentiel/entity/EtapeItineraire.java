package cm.danayexpress.backend.referentiel.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
 * Position ordonnée d'une agence dans un Sens donné (RM-07 : les
 * agences traversées par un voyage sont ordonnées selon la ligne et
 * le sens). Exemple pour le sens ALLER de la ligne Yagoua-Garoua :
 * Yagoua (ordre 1), Kalfou (2), Guidiguis (3), Kaélé (4), Figuil (5),
 * Garoua (6).
 */
@Entity
@Table(name = "etapes_itineraire")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class EtapeItineraire extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sens_id", nullable = false)
    private Sens sens;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agence_id", nullable = false)
    private Agence agence;

    /** Position dans l'itinéraire, à partir de 1 (voir contrainte ck_etapes_ordre_positif). */
    @Column(name = "ordre", nullable = false)
    private Integer ordre;
}