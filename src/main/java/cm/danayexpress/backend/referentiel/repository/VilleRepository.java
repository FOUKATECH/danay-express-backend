package cm.danayexpress.backend.referentiel.repository;

import cm.danayexpress.backend.referentiel.entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VilleRepository extends JpaRepository<Ville, Long> {

    Optional<Ville> findByNomIgnoreCase(String nom);
}