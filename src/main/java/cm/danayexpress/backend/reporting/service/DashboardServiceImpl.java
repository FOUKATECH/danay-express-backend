package cm.danayexpress.backend.reporting.service;

import cm.danayexpress.backend.exploitation.entity.Voyage;
import cm.danayexpress.backend.exploitation.enums.StatutVoyage;
import cm.danayexpress.backend.exploitation.mapper.VoyageMapper;
import cm.danayexpress.backend.exploitation.repository.VoyageRepository;
import cm.danayexpress.backend.finances.entity.Recette;
import cm.danayexpress.backend.finances.repository.RecetteRepository;
import cm.danayexpress.backend.incidents.enums.StatutIncident;
import cm.danayexpress.backend.incidents.repository.IncidentRepository;
import cm.danayexpress.backend.maintenance.repository.InterventionMaintenanceRepository;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;
import cm.danayexpress.backend.parcautomobile.repository.VehiculeRepository;
import cm.danayexpress.backend.referentiel.entity.Agence;
import cm.danayexpress.backend.referentiel.repository.AgenceRepository;
import cm.danayexpress.backend.reporting.dto.DashboardDirectionResponse;
import cm.danayexpress.backend.reporting.dto.DashboardOperationnelResponse;
import cm.danayexpress.backend.reporting.dto.RecetteParAgenceDto;
import cm.danayexpress.backend.reporting.dto.RecetteParLigneDto;
import cm.danayexpress.backend.reporting.dto.RecetteParVehiculeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final VehiculeRepository vehiculeRepository;
    private final VoyageRepository voyageRepository;
    private final IncidentRepository incidentRepository;
    private final InterventionMaintenanceRepository maintenanceRepository;
    private final RecetteRepository recetteRepository;
    private final AgenceRepository agenceRepository;
    private final VoyageMapper voyageMapper;

    @Override
    public DashboardOperationnelResponse getDashboardOperationnel() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        long totalVehicules = vehicules.size();

        Map<String, Long> vehiculesParStatut = new HashMap<>();
        for (StatutVehicule s : StatutVehicule.values()) {
            vehiculesParStatut.put(s.name(), 0L);
        }
        for (Vehicule v : vehicules) {
            String s = v.getStatut().name();
            vehiculesParStatut.put(s, vehiculesParStatut.getOrDefault(s, 0L) + 1);
        }

        List<Voyage> voyages = voyageRepository.findAll();
        long programmes = voyages.stream().filter(v -> v.getStatut() == StatutVoyage.PROGRAMME).count();
        long enCours = voyages.stream().filter(v -> v.getStatut() == StatutVoyage.EN_VOYAGE).count();
        long termines = voyages.stream().filter(v -> v.getStatut() == StatutVoyage.TERMINE).count();

        long incidentsActifs = incidentRepository.findAll().stream()
                .filter(i -> i.getStatut() != StatutIncident.RESOLU && i.getStatut() != StatutIncident.CLOTURE)
                .count();

        long secoursMobilises = vehicules.stream()
                .filter(v -> v.getStatut() == StatutVehicule.EN_SECOURS)
                .count();

        var prochainsDeparts = voyages.stream()
                .filter(v -> v.getStatut() == StatutVoyage.PROGRAMME)
                .sorted((v1, v2) -> {
                    if (v1.getHeureDepartPrevue() == null) return 1;
                    if (v2.getHeureDepartPrevue() == null) return -1;
                    return v1.getHeureDepartPrevue().compareTo(v2.getHeureDepartPrevue());
                })
                .limit(5)
                .map(voyageMapper::toResponse)
                .toList();

        var dernieresArrivees = voyages.stream()
                .filter(v -> v.getStatut() == StatutVoyage.TERMINE && v.getHeureArriveeReelle() != null)
                .sorted((v1, v2) -> v2.getHeureArriveeReelle().compareTo(v1.getHeureArriveeReelle()))
                .limit(5)
                .map(voyageMapper::toResponse)
                .toList();

        return new DashboardOperationnelResponse(
                totalVehicules,
                vehiculesParStatut,
                programmes,
                enCours,
                termines,
                incidentsActifs,
                secoursMobilises,
                prochainsDeparts,
                dernieresArrivees
        );
    }

    @Override
    public DashboardDirectionResponse getDashboardDirection(Instant debut, Instant fin) {
        List<Voyage> voyages = voyageRepository.findAll();
        long totalVoyages = voyages.size();

        long totalPassagers = voyages.stream()
                .mapToLong(v -> v.getEffectifPassagersDepart() != null ? v.getEffectifPassagersDepart() : 0)
                .sum();

        List<Recette> recettes = recetteRepository.findAll();
        BigDecimal recettesTotales = recettes.stream()
                .map(Recette::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Vehicule> vehicules = vehiculeRepository.findAll();
        long totalVehicules = vehicules.size();
        long vehiculesDisponibles = vehicules.stream()
                .filter(v -> v.getStatut() == StatutVehicule.DISPONIBLE || v.getStatut() == StatutVehicule.EN_VOYAGE)
                .count();

        double tauxDisponibilite = totalVehicules > 0 ? ((double) vehiculesDisponibles / totalVehicules) * 100.0 : 0.0;

        long totalIncidents = incidentRepository.count();
        long totalMaintenances = maintenanceRepository.count();

        // Calcul des recettes par agence d'origine
        Map<Long, BigDecimal> recettesAgenceMap = new HashMap<>();
        Map<Long, Long> passagersAgenceMap = new HashMap<>();

        for (Recette r : recettes) {
            if (r.getMouvementTransit() != null && r.getMouvementTransit().getAgenceOrigine() != null) {
                Agence agence = r.getMouvementTransit().getAgenceOrigine();
                recettesAgenceMap.put(agence.getId(), recettesAgenceMap.getOrDefault(agence.getId(), BigDecimal.ZERO).add(r.getMontant()));
                Integer nb = r.getMouvementTransit().getNombrePassagers();
                passagersAgenceMap.put(agence.getId(), passagersAgenceMap.getOrDefault(agence.getId(), 0L) + (nb != null ? nb.longValue() : 0L));
            }
        }

        List<RecetteParAgenceDto> recettesParAgence = new ArrayList<>();
        List<Agence> agences = agenceRepository.findAll();
        for (Agence a : agences) {
            BigDecimal total = recettesAgenceMap.getOrDefault(a.getId(), BigDecimal.ZERO);
            long passagers = passagersAgenceMap.getOrDefault(a.getId(), 0L);
            recettesParAgence.add(new RecetteParAgenceDto(a.getId(), a.getNom(), total, passagers));
        }

        List<RecetteParLigneDto> recettesParLigne = new ArrayList<>();
        List<RecetteParVehiculeDto> topVehicules = new ArrayList<>();

        return new DashboardDirectionResponse(
                totalVoyages,
                totalPassagers,
                recettesTotales,
                Math.round(tauxDisponibilite * 100.0) / 100.0,
                totalIncidents,
                totalMaintenances,
                recettesParAgence,
                recettesParLigne,
                topVehicules
        );
    }
}
