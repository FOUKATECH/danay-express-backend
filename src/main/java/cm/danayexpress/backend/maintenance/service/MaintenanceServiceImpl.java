package cm.danayexpress.backend.maintenance.service;

import cm.danayexpress.backend.incidents.entity.Incident;
import cm.danayexpress.backend.incidents.repository.IncidentRepository;
import cm.danayexpress.backend.maintenance.dto.CloturerMaintenanceRequest;
import cm.danayexpress.backend.maintenance.dto.MaintenanceCreateRequest;
import cm.danayexpress.backend.maintenance.dto.MaintenanceResponse;
import cm.danayexpress.backend.maintenance.dto.MaintenanceUpdateRequest;
import cm.danayexpress.backend.maintenance.entity.InterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.StatutInterventionMaintenance;
import cm.danayexpress.backend.maintenance.enums.TypeInterventionMaintenance;
import cm.danayexpress.backend.maintenance.exception.MaintenanceConflictException;
import cm.danayexpress.backend.maintenance.exception.MaintenanceNotFoundException;
import cm.danayexpress.backend.maintenance.mapper.MaintenanceMapper;
import cm.danayexpress.backend.maintenance.repository.InterventionMaintenanceRepository;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;
import cm.danayexpress.backend.parcautomobile.repository.VehiculeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MaintenanceServiceImpl implements MaintenanceService {

    private final InterventionMaintenanceRepository maintenanceRepository;
    private final VehiculeRepository vehiculeRepository;
    private final IncidentRepository incidentRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    @Transactional
    public MaintenanceResponse creerMaintenance(MaintenanceCreateRequest request) {
        log.info("Création d'une intervention de maintenance pour le véhicule ID={}", request.vehiculeId());

        Vehicule vehicule = vehiculeRepository.findById(request.vehiculeId())
                .orElseThrow(() -> new MaintenanceNotFoundException("Véhicule", request.vehiculeId()));

        if (vehicule.getStatut() == StatutVehicule.EN_VOYAGE) {
            throw new MaintenanceConflictException("Le véhicule est actuellement en voyage. Un voyage doit être clôturé avant d'entrer en maintenance.");
        }

        Incident incident = null;
        if (request.incidentId() != null) {
            incident = incidentRepository.findById(request.incidentId())
                    .orElseThrow(() -> new MaintenanceNotFoundException("Incident", request.incidentId()));
        }

        InterventionMaintenance intervention = maintenanceMapper.toEntity(request);
        intervention.setVehicule(vehicule);
        intervention.setIncident(incident);
        if (intervention.getStatut() == null) {
            intervention.setStatut(StatutInterventionMaintenance.EN_COURS);
        }
        if (intervention.getDateEntree() == null) {
            intervention.setDateEntree(Instant.now());
        }

        // Mettre le véhicule en statut EN_MAINTENANCE
        if (intervention.getStatut() == StatutInterventionMaintenance.EN_COURS || intervention.getStatut() == StatutInterventionMaintenance.PLANIFIEE) {
            vehicule.setStatut(StatutVehicule.EN_MAINTENANCE);
            vehiculeRepository.save(vehicule);
        }

        InterventionMaintenance saved = maintenanceRepository.save(intervention);
        log.info("Intervention de maintenance créée avec succès ID={}", saved.getId());

        return maintenanceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MaintenanceResponse modifierMaintenance(Long id, MaintenanceUpdateRequest request) {
        InterventionMaintenance intervention = findEntityById(id);

        if (intervention.getStatut() == StatutInterventionMaintenance.TERMINEE || intervention.getStatut() == StatutInterventionMaintenance.ANNULEE) {
            throw new MaintenanceConflictException("Impossible de modifier une intervention de maintenance terminée ou annulée.");
        }

        maintenanceMapper.updateEntityFromRequest(request, intervention);

        // Si le statut est passé à TERMINEE via la modification
        if (request.statut() == StatutInterventionMaintenance.TERMINEE) {
            if (intervention.getDateSortieReelle() == null) {
                intervention.setDateSortieReelle(Instant.now());
            }
            Vehicule v = intervention.getVehicule();
            if (v.getStatut() == StatutVehicule.EN_MAINTENANCE) {
                v.setStatut(StatutVehicule.DISPONIBLE);
                vehiculeRepository.save(v);
            }
        }

        InterventionMaintenance saved = maintenanceRepository.save(intervention);
        return maintenanceMapper.toResponse(saved);
    }

    @Override
    public MaintenanceResponse getMaintenanceById(Long id) {
        return maintenanceMapper.toResponse(findEntityById(id));
    }

    @Override
    public List<MaintenanceResponse> listerMaintenances(Long vehiculeId, StatutInterventionMaintenance statut, TypeInterventionMaintenance typeIntervention) {
        List<InterventionMaintenance> list = maintenanceRepository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();
            if (vehiculeId != null) {
                predicates.getExpressions().add(cb.equal(root.get("vehicule").get("id"), vehiculeId));
            }
            if (statut != null) {
                predicates.getExpressions().add(cb.equal(root.get("statut"), statut));
            }
            if (typeIntervention != null) {
                predicates.getExpressions().add(cb.equal(root.get("typeIntervention"), typeIntervention));
            }
            return predicates;
        });

        return list.stream()
                .map(maintenanceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public MaintenanceResponse cloturerMaintenance(Long id, CloturerMaintenanceRequest request) {
        InterventionMaintenance intervention = findEntityById(id);

        if (intervention.getStatut() == StatutInterventionMaintenance.TERMINEE) {
            throw new MaintenanceConflictException("Cette intervention de maintenance est déjà clôturée.");
        }

        if (request.travauxRealises() != null && !request.travauxRealises().isBlank()) {
            intervention.setTravauxRealises(request.travauxRealises());
        }
        if (request.coutTotal() != null) {
            intervention.setCoutTotal(request.coutTotal());
        }
        intervention.setDateSortieReelle(request.dateSortieReelle() != null ? request.dateSortieReelle() : Instant.now());
        intervention.setStatut(StatutInterventionMaintenance.TERMINEE);

        // Libérer le véhicule et le remettre à l'état DISPONIBLE (CDC Section 8.8)
        Vehicule vehicule = intervention.getVehicule();
        if (vehicule.getStatut() == StatutVehicule.EN_MAINTENANCE) {
            vehicule.setStatut(StatutVehicule.DISPONIBLE);
            vehiculeRepository.save(vehicule);
        }

        InterventionMaintenance saved = maintenanceRepository.save(intervention);
        log.info("Intervention de maintenance ID={} clôturée. Véhicule ID={} remis en disponibilité.", saved.getId(), vehicule.getId());

        return maintenanceMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MaintenanceResponse annulerMaintenance(Long id, String motif) {
        InterventionMaintenance intervention = findEntityById(id);

        if (intervention.getStatut() == StatutInterventionMaintenance.TERMINEE) {
            throw new MaintenanceConflictException("Impossible d'annuler une intervention déjà terminée.");
        }

        intervention.setStatut(StatutInterventionMaintenance.ANNULEE);
        if (motif != null && !motif.isBlank()) {
            intervention.setDescriptionPanneMotif(
                    (intervention.getDescriptionPanneMotif() != null ? intervention.getDescriptionPanneMotif() + " | [Annulation]: " : "[Annulation]: ") + motif
            );
        }

        // Remettre le véhicule en disponibilité s'il était bloqué en maintenance
        Vehicule vehicule = intervention.getVehicule();
        if (vehicule.getStatut() == StatutVehicule.EN_MAINTENANCE) {
            vehicule.setStatut(StatutVehicule.DISPONIBLE);
            vehiculeRepository.save(vehicule);
        }

        InterventionMaintenance saved = maintenanceRepository.save(intervention);
        return maintenanceMapper.toResponse(saved);
    }

    private InterventionMaintenance findEntityById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new MaintenanceNotFoundException("Intervention de maintenance", id));
    }
}
