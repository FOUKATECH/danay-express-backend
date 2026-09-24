package cm.danayexpress.backend.administration.repository;

import cm.danayexpress.backend.administration.entity.Utilisateur;
import cm.danayexpress.backend.administration.enums.StatutUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>, JpaSpecificationExecutor<Utilisateur> {

    Optional<Utilisateur> findByNomUtilisateur(String nomUtilisateur);

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByNomUtilisateur(String nomUtilisateur);

    boolean existsByEmail(String email);

    List<Utilisateur> findByStatut(StatutUtilisateur statut);

    List<Utilisateur> findByRoleId(Long roleId);

    List<Utilisateur> findByAgenceId(Long agenceId);
}
