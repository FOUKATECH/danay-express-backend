package cm.danayexpress.backend.referentiel.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
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
 * Agence du réseau Danay Express (CDC section 8.1).
 * Une agence est rattachée à une ville, et peut apparaître comme
 * agence de départ/arrivée d'un Sens, ou comme étape intermédiaire
 * via EtapeItineraire.
 */
@Entity
@Table(name = "agences")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Agence extends AuditableEntity {

    @Column(name = "code", nullable = false, length = 20, unique = true)
    private String code;

    @Column(name = "nom", nullable = false, length = 150)
    private String nom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ville_id", nullable = false)
    private Ville ville;

    @Column(name = "adresse", length = 255)
    private String adresse;

    @Column(name = "telephone", length = 30)
    private String telephone;

    @Column(name = "responsable", length = 150)
    private String responsable;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    @Builder.Default
    private StatutReferentiel statut = StatutReferentiel.ACTIVE;
}