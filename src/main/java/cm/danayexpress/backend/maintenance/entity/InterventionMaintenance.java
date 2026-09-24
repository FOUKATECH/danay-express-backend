package cm.danayexpress.backend.maintenance.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.incidents.entity.Incident;
import cm.danayexpress.backend.maintenance.enums.StatutInterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.TypeInterventionMaintenance;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
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

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Operation de maintenance d'un véhicule (CDC section 8.8 & 17).
 */
@Entity
@Table(name = "interventions_maintenance")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class InterventionMaintenance extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id")
    private Incident incident;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_intervention", nullable = false, length = 50)
    private TypeInterventionMaintenance typeIntervention;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 30)
    @Builder.Default
    private StatutInterventionMaintenance statut = StatutInterventionMaintenance.EN_COURS;

    @Column(name = "description_panne_motif", nullable = false, columnDefinition = "TEXT")
    private String descriptionPanneMotif;

    @Column(name = "travaux_realises", columnDefinition = "TEXT")
    private String travauxRealises;

    @Column(name = "garage_prestataire")
    private String garagePrestataire;

    @Column(name = "cout_total", precision = 12, scale = 2)
    private BigDecimal coutTotal;

    @Column(name = "date_entree", nullable = false)
    @Builder.Default
    private Instant dateEntree = Instant.now();

    @Column(name = "date_sortie_prevue")
    private Instant dateSortiePrevue;

    @Column(name = "date_sortie_reelle")
    private Instant dateSortieReelle;
}
