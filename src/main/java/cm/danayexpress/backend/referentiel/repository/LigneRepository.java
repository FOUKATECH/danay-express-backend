package cm.danayexpress.backend.referentiel.repository;

import cm.danayexpress.backend.referentiel.entity.Ligne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LigneRepository extends JpaRepository<Ligne, Long> {

    Optional<Ligne> findByCode(String code);
}