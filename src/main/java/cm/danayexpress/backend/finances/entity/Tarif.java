package cm.danayexpress.backend.finances.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.finances.enums.StatutTarif;
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

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Tarif applicable pour un trajet origine → destination et un type de
 * véhicule donné (CDC section 8.6). Le type de véhicule est comparé en
 * texte libre à Vehicule.type, cohérent avec ce champ côté Parc
 * Automobile (pas de liste fermée définie dans le CDC).
 */
@Entity
@Table(name = "tarifs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Tarif extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "origine_id", nullable = false)
    private Agence origine;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destination_id", nullable = false)
    private Agence destination;

    @Column(name = "type_vehicule", nullable = false, length = 50)
    private String typeVehicule;

    @Column(name = "montant", nullable = false, precision = 12, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    /** Null = tarif applicable indéfiniment (pas de date de fin connue). */
    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    @Builder.Default
    private StatutTarif statut = StatutTarif.ACTIF;
}