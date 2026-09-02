package cm.danayexpress.backend.referentiel.entity;

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
 * Ville desservie par le réseau Danay Express. Une agence est
 * toujours rattachée à une ville (CDC section 8.1).
 */
@Entity
@Table(name = "villes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Ville extends AuditableEntity {

    @Column(name = "nom", nullable = false, length = 150, unique = true)
    private String nom;

    @Column(name = "region", length = 150)
    private String region;
}