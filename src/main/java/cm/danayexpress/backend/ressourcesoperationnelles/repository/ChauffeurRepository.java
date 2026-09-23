package cm.danayexpress.backend.ressourcesoperationnelles.repository;

import cm.danayexpress.backend.ressourcesoperationnelles.entity.Chauffeur;
import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutChauffeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {

    Optional<Chauffeur> findByNumeroPermis(String numeroPermis);

    List<Chauffeur> findByStatut(StatutChauffeur statut);
}