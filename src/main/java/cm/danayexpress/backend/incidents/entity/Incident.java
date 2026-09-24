package cm.danayexpress.backend.incidents.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.exploitation.entity.Voyage;
import cm.danayexpress.backend.incidents.enums.GraviteIncident;
import cm.danayexpress.backend.incidents.enums.StatutIncident;
import cm.danayexpress.backend.incidents.enums.TypeIncident;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
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

import java.time.Instant;

/**
 * Incident survenu sur un véhicule / voyage (CDC section 8.7 & 17).
 */
@Entity
@Table(name = "incidents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Incident extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voyage_id")
    private Voyage voyage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_id")
    private Agence agence;

    @Column(name = "localisation_declaree")
    private String localisationDeclaree;

    @Column(name = "date_heure_incident", nullable = false)
    @Builder.Default
    private Instant dateHeureIncident = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "type_incident", nullable = false, length = 50)
    private TypeIncident typeIncident;

    @Enumerated(EnumType.STRING)
    @Column(name = "gravite", nullable = false, length = 20)
    private GraviteIncident gravite;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "nombre_passagers_concernes")
    private Integer nombrePassagersConcernes;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 30)
    @Builder.Default
    private StatutIncident statut = StatutIncident.SIGNALE;
}
