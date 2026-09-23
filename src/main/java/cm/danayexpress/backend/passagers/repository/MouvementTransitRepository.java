package cm.danayexpress.backend.passagers.repository;

import cm.danayexpress.backend.passagers.entity.MouvementTransit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MouvementTransitRepository extends JpaRepository<MouvementTransit, Long> {

    List<MouvementTransit> findByVoyageIdOrderByCreatedAtAsc(Long voyageId);
}