package cm.danayexpress.backend.incidents.service;

import cm.danayexpress.backend.exploitation.entity.Voyage;
import cm.danayexpress.backend.exploitation.repository.VoyageRepository;
import cm.danayexpress.backend.incidents.dto.AffecterSecoursRequest;
import cm.danayexpress.backend.incidents.dto.ChangerStatutInterventionRequest;
import cm.danayexpress.backend.incidents.dto.IncidentCreateRequest;
import cm.danayexpress.backend.incidents.dto.IncidentResponse;
import cm.danayexpress.backend.incidents.dto.IncidentUpdateRequest;
import cm.danayexpress.backend.incidents.dto.InterventionSecoursResponse;
import cm.danayexpress.backend.incidents.entity.Incident;
import cm.danayexpress.backend.incidents.entity.InterventionSecours;
import cm.danayexpress.backend.incidents.enums.GraviteIncident;
import cm.danayexpress.backend.incidents.enums.StatutIncident;
import cm.danayexpress.backend.incidents.enums.StatutInterventionSecours;
import cm.danayexpress.backend.incidents.enums.TypeIncident;
import cm.danayexpress.backend.incidents.exception.IncidentsConflictException;
import cm.danayexpress.backend.incidents.exception.IncidentsNotFoundException;
import cm.danayexpress.backend.incidents.mapper.IncidentMapper;
import cm.danayexpress.backend.incidents.mapper.InterventionSecoursMapper;
import cm.danayexpress.backend.incidents.repository.IncidentRepository;
import cm.danayexpress.backend.incidents.repository.InterventionSecoursRepository;
import cm.danayexpress.backend.parcautomobile.dto.VehiculeResponse;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;
import cm.danayexpress.backend.parcautomobile.mapper.VehiculeMapper;
import cm.danayexpress.backend.parcautomobile.repository.VehiculeRepository;
import cm.danayexpress.backend.referentiel.entity.Agence;
import cm.danayexpress.backend.referentiel.repository.AgenceRepository;
import cm.danayexpress.backend.ressourcesoperationnelles.entity.Chauffeur;
import cm.danayexpress.backend.ressourcesoperationnelles.repository.ChauffeurRepository;
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
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final InterventionSecoursRepository interventionSecoursRepository;
    private final VehiculeRepository vehiculeRepository;
    private final VoyageRepository voyageRepository;
    private final AgenceRepository agenceRepository;
    private final ChauffeurRepository chauffeurRepository;

    private final IncidentMapper incidentMapper;
    private final InterventionSecoursMapper interventionSecoursMapper;
    private final VehiculeMapper vehiculeMapper;

    @Override
    @Transactional
    public IncidentResponse declarerIncident(IncidentCreateRequest request) {
        log.info("Déclaration d'un incident pour le véhicule ID={}", request.vehiculeId());

        Vehicule vehicule = vehiculeRepository.findById(request.vehiculeId())
                .orElseThrow(() -> new IncidentsNotFoundException("Véhicule", request.vehiculeId()));

        Voyage voyage = null;
        if (request.voyageId() != null) {
            voyage = voyageRepository.findById(request.voyageId())
                    .orElseThrow(() -> new IncidentsNotFoundException("Voyage", request.voyageId()));
        }

        Agence agence = null;
        if (request.agenceId() != null) {
            agence = agenceRepository.findById(request.agenceId())
                    .orElseThrow(() -> new IncidentsNotFoundException("Agence", request.agenceId()));
        }

        Incident incident = incidentMapper.toEntity(request);
        incident.setVehicule(vehicule);
        incident.setVoyage(voyage);
        incident.setAgence(agence);
        incident.setStatut(StatutIncident.SIGNALE);
        if (incident.getDateHeureIncident() == null) {
            incident.setDateHeureIncident(Instant.now());
        }

        // Si l'incident est une panne mécanique grave ou un accident, mettre à jour le statut du véhicule
        if (request.typeIncident() == TypeIncident.PANNE_MECANIQUE || request.gravite() == GraviteIncident.CRITIQUE) {
            vehicule.setStatut(StatutVehicule.EN_PANNE);
            vehiculeRepository.save(vehicule);
        }

        Incident saved = incidentRepository.save(incident);
        log.info("Incident créé avec succès ID={}", saved.getId());

        return incidentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public IncidentResponse modifierIncident(Long id, IncidentUpdateRequest request) {
        Incident incident = findIncidentEntity(id);

        if (incident.getStatut() == StatutIncident.CLOTURE) {
            throw new IncidentsConflictException("Impossible de modifier un incident déjà clôturé");
        }

        if (request.agenceId() != null) {
            Agence agence = agenceRepository.findById(request.agenceId())
                    .orElseThrow(() -> new IncidentsNotFoundException("Agence", request.agenceId()));
            incident.setAgence(agence);
        }

        incidentMapper.updateEntityFromRequest(request, incident);
        Incident saved = incidentRepository.save(incident);

        return incidentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public IncidentResponse modifierStatutIncident(Long id, StatutIncident statut) {
        Incident incident = findIncidentEntity(id);
        incident.setStatut(statut);
        Incident saved = incidentRepository.save(incident);
        return incidentMapper.toResponse(saved);
    }

    @Override
    public IncidentResponse getIncidentById(Long id) {
        return incidentMapper.toResponse(findIncidentEntity(id));
    }

    @Override
    public List<IncidentResponse> listerIncidents(StatutIncident statut, GraviteIncident gravite, Long vehiculeId, Long voyageId, Long agenceId) {
        List<Incident> incidents = incidentRepository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();
            if (statut != null) {
                predicates.getExpressions().add(cb.equal(root.get("statut"), statut));
            }
            if (gravite != null) {
                predicates.getExpressions().add(cb.equal(root.get("gravite"), gravite));
            }
            if (vehiculeId != null) {
                predicates.getExpressions().add(cb.equal(root.get("vehicule").get("id"), vehiculeId));
            }
            if (voyageId != null) {
                predicates.getExpressions().add(cb.equal(root.get("voyage").get("id"), voyageId));
            }
            if (agenceId != null) {
                predicates.getExpressions().add(cb.equal(root.get("agence").get("id"), agenceId));
            }
            return predicates;
        });

        return incidents.stream()
                .map(incidentMapper::toResponse)
                .toList();
    }

    @Override
    public List<VehiculeResponse> rechercherVehiculesSecoursDisponibles(Long agenceId) {
        List<Vehicule> vehicules;
        if (agenceId != null) {
            vehicules = vehiculeRepository.findByAgenceId(agenceId).stream()
                    .filter(v -> v.getStatut() == StatutVehicule.DISPONIBLE)
                    .toList();
        } else {
            vehicules = vehiculeRepository.findByStatut(StatutVehicule.DISPONIBLE);
        }

        return vehicules.stream()
                .map(vehiculeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InterventionSecoursResponse affecterVehiculeSecours(Long incidentId, AffecterSecoursRequest request) {
        Incident incident = findIncidentEntity(incidentId);

        if (incident.getStatut() == StatutIncident.CLOTURE || incident.getStatut() == StatutIncident.RESOLU) {
            throw new IncidentsConflictException("L'incident est déjà résolu ou clôturé");
        }

        Vehicule vehiculeSecours = vehiculeRepository.findById(request.vehiculeSecoursId())
                .orElseThrow(() -> new IncidentsNotFoundException("Véhicule de secours", request.vehiculeSecoursId()));

        if (vehiculeSecours.getStatut() != StatutVehicule.DISPONIBLE) {
            throw new IncidentsConflictException("Le véhicule " + vehiculeSecours.getImmatriculation() + " n'est pas disponible (statut actuel: " + vehiculeSecours.getStatut() + ")");
        }

        Chauffeur chauffeur = null;
        if (request.chauffeurSecoursId() != null) {
            chauffeur = chauffeurRepository.findById(request.chauffeurSecoursId())
                    .orElseThrow(() -> new IncidentsNotFoundException("Chauffeur", request.chauffeurSecoursId()));
        }

        // Met à jour le statut du véhicule de secours à EN_SECOURS
        vehiculeSecours.setStatut(StatutVehicule.EN_SECOURS);
        vehiculeRepository.save(vehiculeSecours);

        // Crée l'intervention
        InterventionSecours intervention = InterventionSecours.builder()
                .incident(incident)
                .vehiculeSecours(vehiculeSecours)
                .chauffeurSecours(chauffeur)
                .dateHeureAffectation(Instant.now())
                .statut(StatutInterventionSecours.AFFECTE)
                .observations(request.observations())
                .build();

        InterventionSecours saved = interventionSecoursRepository.save(intervention);

        // Met à jour le statut de l'incident à SECOURS_AFFECTE
        incident.setStatut(StatutIncident.SECOURS_AFFECTE);
        incidentRepository.save(incident);

        log.info("Véhicule de secours ID={} affecté à l'incident ID={}", vehiculeSecours.getId(), incident.getId());
        return interventionSecoursMapper.toResponse(saved);
    }

    @Override
    public List<InterventionSecoursResponse> getInterventionsParIncident(Long incidentId) {
        findIncidentEntity(incidentId);
        return interventionSecoursRepository.findByIncidentId(incidentId).stream()
                .map(interventionSecoursMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InterventionSecoursResponse changerStatutIntervention(Long interventionId, ChangerStatutInterventionRequest request) {
        InterventionSecours intervention = interventionSecoursRepository.findById(interventionId)
                .orElseThrow(() -> new IncidentsNotFoundException("Intervention de secours", interventionId));

        StatutInterventionSecours nouveauStatut = request.nouveauStatut();
        intervention.setStatut(nouveauStatut);
        if (request.observations() != null && !request.observations().isBlank()) {
            intervention.setObservations(
                    (intervention.getObservations() != null ? intervention.getObservations() + " | " : "") + request.observations()
            );
        }

        Instant now = Instant.now();
        if (nouveauStatut == StatutInterventionSecours.EN_ROUTE && intervention.getDateHeureDepart() == null) {
            intervention.setDateHeureDepart(now);
        } else if (nouveauStatut == StatutInterventionSecours.PRIS_EN_CHARGE && intervention.getDateHeurePriseEnCharge() == null) {
            intervention.setDateHeurePriseEnCharge(now);
        } else if (nouveauStatut == StatutInterventionSecours.TERMINE && intervention.getDateHeureResolution() == null) {
            intervention.setDateHeureResolution(now);
            // Libérer le véhicule de secours si terminé
            Vehicule vSecours = intervention.getVehiculeSecours();
            if (vSecours.getStatut() == StatutVehicule.EN_SECOURS) {
                vSecours.setStatut(StatutVehicule.DISPONIBLE);
                vehiculeRepository.save(vSecours);
            }
        } else if (nouveauStatut == StatutInterventionSecours.ANNULE) {
            // Libérer le véhicule de secours si annulé
            Vehicule vSecours = intervention.getVehiculeSecours();
            if (vSecours.getStatut() == StatutVehicule.EN_SECOURS) {
                vSecours.setStatut(StatutVehicule.DISPONIBLE);
                vehiculeRepository.save(vSecours);
            }
        }

        InterventionSecours saved = interventionSecoursRepository.save(intervention);
        return interventionSecoursMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public IncidentResponse cloturerIncident(Long incidentId, String observations) {
        Incident incident = findIncidentEntity(incidentId);

        if (incident.getStatut() == StatutIncident.CLOTURE) {
            throw new IncidentsConflictException("L'incident est déjà clôturé");
        }

        // Vérifier et terminer toutes les interventions de secours encore actives
        List<InterventionSecours> interventions = interventionSecoursRepository.findByIncidentId(incidentId);
        for (InterventionSecours intervention : interventions) {
            if (intervention.getStatut() != StatutInterventionSecours.TERMINE && intervention.getStatut() != StatutInterventionSecours.ANNULE) {
                intervention.setStatut(StatutInterventionSecours.TERMINE);
                intervention.setDateHeureResolution(Instant.now());
                interventionSecoursRepository.save(intervention);

                // Libérer véhicule de secours
                Vehicule vSecours = intervention.getVehiculeSecours();
                if (vSecours.getStatut() == StatutVehicule.EN_SECOURS) {
                    vSecours.setStatut(StatutVehicule.DISPONIBLE);
                    vehiculeRepository.save(vSecours);
                }
            }
        }

        if (observations != null && !observations.isBlank()) {
            incident.setDescription((incident.getDescription() != null ? incident.getDescription() + " | [Clôture]: " : "[Clôture]: ") + observations);
        }
        incident.setStatut(StatutIncident.CLOTURE);

        Incident saved = incidentRepository.save(incident);
        log.info("Incident ID={} clôturé", saved.getId());
        return incidentMapper.toResponse(saved);
    }

    private Incident findIncidentEntity(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new IncidentsNotFoundException("Incident", id));
    }
}
