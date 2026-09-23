package cm.danayexpress.backend.parcautomobile.entity;

import cm.danayexpress.backend.common.audit.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Propriétaire d'un ou plusieurs véhicules (CDC section 8.2).
 */
@Entity
@Table(name = "proprietaires")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Proprietaire extends AuditableEntity {

    @Column(name = "nom", nullable = false, length = 150)
    private String nom;

    @Column(name = "telephone", length = 30)
    private String telephone;

    @Column(name = "adresse", length = 255)
    private String adresse;
}