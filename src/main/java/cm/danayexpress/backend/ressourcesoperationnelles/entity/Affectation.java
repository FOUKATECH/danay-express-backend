package cm.danayexpress.backend.ressourcesoperationnelles.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.exploitation.entity.Voyage;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutAffectation;
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

import java.time.LocalDate;

/**
 * Affectation d'un chauffeur à un véhicule (CDC section 8.3), avec un
 * lien optionnel vers un Voyage précis depuis le module Exploitation
 * (CDC : "affecter un chauffeur à un véhicule ou à un voyage").
 */
@Entity
@Table(name = "affectations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Affectation extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chauffeur_id", nullable = false)
    private Chauffeur chauffeur;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

    /** Null si l'affectation concerne le véhicule seul, sans voyage précis. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voyage_id")
    private Voyage voyage;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    /** Null tant que l'affectation est en cours (statut ACTIVE). */
    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    @Builder.Default
    private StatutAffectation statut = StatutAffectation.ACTIVE;
}