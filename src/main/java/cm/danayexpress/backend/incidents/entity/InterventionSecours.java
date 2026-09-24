package cm.danayexpress.backend.incidents.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.incidents.enums.StatutInterventionSecours;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import cm.danayexpress.backend.ressourcesoperationnelles.entity.Chauffeur;
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

import java.time.Instant;

/**
 * Intervention de secours liée à un incident (CDC section 8.7 & 17).
 */
@Entity
@Table(name = "interventions_secours")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class InterventionSecours extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incident_id", nullable = false)
    private Incident incident;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicule_secours_id", nullable = false)
    private Vehicule vehiculeSecours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chauffeur_secours_id")
    private Chauffeur chauffeurSecours;

    @Column(name = "date_heure_affectation", nullable = false)
    @Builder.Default
    private Instant dateHeureAffectation = Instant.now();

    @Column(name = "date_heure_depart")
    private Instant dateHeureDepart;

    @Column(name = "date_heure_prise_en_charge")
    private Instant dateHeurePriseEnCharge;

    @Column(name = "date_heure_resolution")
    private Instant dateHeureResolution;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 30)
    @Builder.Default
    private StatutInterventionSecours statut = StatutInterventionSecours.AFFECTE;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;
}
