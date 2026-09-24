package cm.danayexpress.backend.administration.entity;

import cm.danayexpress.backend.administration.enums.StatutUtilisateur;
import cm.danayexpress.backend.common.audit.AuditableEntity;
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
 * Utilisateur de l'application (CDC section 8.13 & 14).
 * Rattaché à un rôle et éventuellement à une agence de référence.
 */
@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "motDePasse")
public class Utilisateur extends AuditableEntity {

    @Column(name = "nom_utilisateur", nullable = false, length = 50, unique = true)
    private String nomUtilisateur;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "telephone", length = 30)
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    @Builder.Default
    private StatutUtilisateur statut = StatutUtilisateur.ACTIF;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_id")
    private Agence agence;

    @Column(name = "dernier_login")
    private Instant dernierLogin;
}
