package cm.danayexpress.backend.parcautomobile.repository;

import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    Optional<Vehicule> findByImmatriculation(String immatriculation);

    List<Vehicule> findByStatut(StatutVehicule statut);

    List<Vehicule> findByProprietaireId(Long proprietaireId);

    List<Vehicule> findByAgenceId(Long agenceId);
}