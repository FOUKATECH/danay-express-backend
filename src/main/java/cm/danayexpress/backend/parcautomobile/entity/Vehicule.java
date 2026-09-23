package cm.danayexpress.backend.parcautomobile.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;
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
 * Véhicule du parc automobile (CDC section 8.2). Rattaché à un
 * propriétaire et à une agence ; son statut évolue au fil des voyages
 * (piloté par le module Exploitation).
 */
@Entity
@Table(name = "vehicules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Vehicule extends AuditableEntity {

    @Column(name = "immatriculation", nullable = false, length = 20, unique = true)
    private String immatriculation;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "capacite", nullable = false)
    private Integer capacite;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proprietaire_id", nullable = false)
    private Proprietaire proprietaire;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agence_id", nullable = false)
    private Agence agence;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    @Builder.Default
    private StatutVehicule statut = StatutVehicule.DISPONIBLE;
}