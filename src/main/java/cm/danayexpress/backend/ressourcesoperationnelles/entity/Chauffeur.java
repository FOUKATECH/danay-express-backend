package cm.danayexpress.backend.ressourcesoperationnelles.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutChauffeur;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Chauffeur (CDC section 8.3). Sa disponibilité se déduit de ses
 * Affectations en cours (statut ACTIVE) plutôt que d'être stockée
 * séparément.
 */
@Entity
@Table(name = "chauffeurs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Chauffeur extends AuditableEntity {

    @Column(name = "nom", nullable = false, length = 150)
    private String nom;

    @Column(name = "prenom", length = 150)
    private String prenom;

    @Column(name = "telephone", length = 30)
    private String telephone;

    @Column(name = "numero_permis", nullable = false, length = 30, unique = true)
    private String numeroPermis;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    @Builder.Default
    private StatutChauffeur statut = StatutChauffeur.ACTIF;
}