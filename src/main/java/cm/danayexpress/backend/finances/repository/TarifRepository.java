package cm.danayexpress.backend.finances.repository;

import cm.danayexpress.backend.finances.entity.Tarif;
import cm.danayexpress.backend.finances.enums.StatutTarif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TarifRepository extends JpaRepository<Tarif, Long> {

    List<Tarif> findByOrigineIdAndDestinationIdAndTypeVehiculeAndStatut(
            Long origineId, Long destinationId, String typeVehicule, StatutTarif statut);

    List<Tarif> findByStatut(StatutTarif statut);

    /** Le tarif applicable à une date donnée, s'il en existe un (voir aussi la validation de non-chevauchement en service). */
    default Optional<Tarif> findApplicable(Long origineId, Long destinationId, String typeVehicule, LocalDate date) {
        return findByOrigineIdAndDestinationIdAndTypeVehiculeAndStatut(origineId, destinationId, typeVehicule, StatutTarif.ACTIF)
                .stream()
                .filter(t -> !t.getDateDebut().isAfter(date))
                .filter(t -> t.getDateFin() == null || !t.getDateFin().isBefore(date))
                .findFirst();
    }
}