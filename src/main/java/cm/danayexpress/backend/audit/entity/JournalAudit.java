package cm.danayexpress.backend.audit.entity;

import cm.danayexpress.backend.audit.enums.ModuleApplicatif;
import cm.danayexpress.backend.audit.enums.TypeActionAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Entité représentant un enregistrement du journal d'audit (CDC section 8.12).
 */
@Entity
@Table(name = "journaux_audit")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class JournalAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_utilisateur", nullable = false, length = 50)
    private String nomUtilisateur;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 50)
    private TypeActionAudit action;

    @Enumerated(EnumType.STRING)
    @Column(name = "module", nullable = false, length = 50)
    private ModuleApplicatif module;

    @Column(name = "element_id", length = 100)
    private String elementId;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "ancienne_valeur", columnDefinition = "TEXT")
    private String ancienneValeur;

    @Column(name = "nouvelle_valeur", columnDefinition = "TEXT")
    private String nouvelleValeur;

    @Column(name = "adresse_ip", length = 45)
    private String adresseIp;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
