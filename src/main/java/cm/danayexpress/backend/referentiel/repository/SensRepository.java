package cm.danayexpress.backend.referentiel.repository;

import cm.danayexpress.backend.referentiel.entity.Sens;
import cm.danayexpress.backend.referentiel.enums.SensCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensRepository extends JpaRepository<Sens, Long> {

    List<Sens> findByLigneId(Long ligneId);

    Optional<Sens> findByLigneIdAndCode(Long ligneId, SensCode code);
}