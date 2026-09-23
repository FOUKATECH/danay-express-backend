package cm.danayexpress.backend.parcautomobile.service;

import cm.danayexpress.backend.parcautomobile.dto.VehiculeRequest;
import cm.danayexpress.backend.parcautomobile.dto.VehiculeResponse;
import cm.danayexpress.backend.parcautomobile.entity.Proprietaire;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;
import cm.danayexpress.backend.parcautomobile.exception.ParcAutomobileConflictException;
import cm.danayexpress.backend.parcautomobile.exception.ParcAutomobileNotFoundException;
import cm.danayexpress.backend.parcautomobile.mapper.VehiculeMapper;
import cm.danayexpress.backend.parcautomobile.repository.ProprietaireRepository;
import cm.danayexpress.backend.parcautomobile.repository.VehiculeRepository;
import cm.danayexpress.backend.referentiel.entity.Agence;
import cm.danayexpress.backend.referentiel.exception.ReferentielNotFoundException;
import cm.danayexpress.backend.referentiel.repository.AgenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final ProprietaireRepository proprietaireRepository;
    private final AgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    public List<VehiculeResponse> findAll() {
        return vehiculeRepository.findAll().stream()
                .map(vehiculeMapper::toResponse)
                .toList();
    }

    public List<VehiculeResponse> findByStatut(StatutVehicule statut) {
        return vehiculeRepository.findByStatut(statut).stream()
                .map(vehiculeMapper::toResponse)
                .toList();
    }

    public VehiculeResponse findById(Long id) {
        return vehiculeMapper.toResponse(getVehiculeOrThrow(id));
    }

    @Transactional
    public VehiculeResponse create(VehiculeRequest request) {
        ensureImmatriculationIsAvailable(request.immatriculation(), null);
        Proprietaire proprietaire = getProprietaireOrThrow(request.proprietaireId());
        Agence agence = getAgenceOrThrow(request.agenceId());

        Vehicule vehicule = vehiculeMapper.toEntity(request);
        vehicule.setProprietaire(proprietaire);
        vehicule.setAgence(agence);
        vehicule.setStatut(request.statut() != null ? request.statut() : StatutVehicule.DISPONIBLE);

        return vehiculeMapper.toResponse(vehiculeRepository.save(vehicule));
    }

    @Transactional
    public VehiculeResponse update(Long id, VehiculeRequest request) {
        Vehicule vehicule = getVehiculeOrThrow(id);
        ensureImmatriculationIsAvailable(request.immatriculation(), id);
        Proprietaire proprietaire = getProprietaireOrThrow(request.proprietaireId());
        Agence agence = getAgenceOrThrow(request.agenceId());

        vehiculeMapper.updateEntityFromRequest(request, vehicule);
        vehicule.setProprietaire(proprietaire);
        vehicule.setAgence(agence);
        vehicule.setStatut(request.statut() != null ? request.statut() : vehicule.getStatut());

        return vehiculeMapper.toResponse(vehiculeRepository.save(vehicule));
    }

    @Transactional
    public void delete(Long id) {
        vehiculeRepository.delete(getVehiculeOrThrow(id));
    }

    private Vehicule getVehiculeOrThrow(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ParcAutomobileNotFoundException("Vehicule", id));
    }

    private Proprietaire getProprietaireOrThrow(Long proprietaireId) {
        return proprietaireRepository.findById(proprietaireId)
                .orElseThrow(() -> new ParcAutomobileNotFoundException("Proprietaire", proprietaireId));
    }

    /** Agence appartient au module Référentiel : on réutilise son exception dédiée. */
    private Agence getAgenceOrThrow(Long agenceId) {
        return agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ReferentielNotFoundException("Agence", agenceId));
    }

    private void ensureImmatriculationIsAvailable(String immatriculation, Long excludedVehiculeId) {
        vehiculeRepository.findByImmatriculation(immatriculation).ifPresent(existing -> {
            if (!existing.getId().equals(excludedVehiculeId)) {
                throw new ParcAutomobileConflictException(
                        "L'immatriculation '" + immatriculation + "' est déjà utilisée.");
            }
        });
    }
}