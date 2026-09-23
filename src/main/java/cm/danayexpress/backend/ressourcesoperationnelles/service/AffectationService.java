package cm.danayexpress.backend.ressourcesoperationnelles.service;

import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import cm.danayexpress.backend.parcautomobile.exception.ParcAutomobileNotFoundException;
import cm.danayexpress.backend.parcautomobile.repository.VehiculeRepository;
import cm.danayexpress.backend.ressourcesoperationnelles.dto.AffectationRequest;
import cm.danayexpress.backend.ressourcesoperationnelles.dto.AffectationResponse;
import cm.danayexpress.backend.ressourcesoperationnelles.entity.Affectation;
import cm.danayexpress.backend.ressourcesoperationnelles.entity.Chauffeur;
import cm.danayexpress.backend.ressourcesoperationnelles.enums.StatutAffectation;
import cm.danayexpress.backend.ressourcesoperationnelles.exception.RessourcesOperationnellesConflictException;
import cm.danayexpress.backend.ressourcesoperationnelles.exception.RessourcesOperationnellesNotFoundException;
import cm.danayexpress.backend.ressourcesoperationnelles.mapper.AffectationMapper;
import cm.danayexpress.backend.ressourcesoperationnelles.repository.AffectationRepository;
import cm.danayexpress.backend.ressourcesoperationnelles.repository.ChauffeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Logique métier des affectations. Fait respecter la règle de
 * disponibilité (CDC section 8.3) : un chauffeur ne peut pas avoir
 * deux affectations ACTIVE en même temps.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AffectationService {

    private final AffectationRepository affectationRepository;
    private final ChauffeurRepository chauffeurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final AffectationMapper affectationMapper;

    public List<AffectationResponse> findAll() {
        return affectationRepository.findAll().stream()
                .map(affectationMapper::toResponse)
                .toList();
    }

    public List<AffectationResponse> findByChauffeurId(Long chauffeurId) {
        return affectationRepository.findByChauffeurIdOrderByDateDebutDesc(chauffeurId).stream()
                .map(affectationMapper::toResponse)
                .toList();
    }

    public List<AffectationResponse> findByVehiculeId(Long vehiculeId) {
        return affectationRepository.findByVehiculeIdOrderByDateDebutDesc(vehiculeId).stream()
                .map(affectationMapper::toResponse)
                .toList();
    }

    public AffectationResponse findById(Long id) {
        return affectationMapper.toResponse(getAffectationOrThrow(id));
    }

    @Transactional
    public AffectationResponse create(AffectationRequest request) {
        Chauffeur chauffeur = getChauffeurOrThrow(request.chauffeurId());
        Vehicule vehicule = getVehiculeOrThrow(request.vehiculeId());
        StatutAffectation statutEffectif = request.statut() != null ? request.statut() : StatutAffectation.ACTIVE;

        if (statutEffectif == StatutAffectation.ACTIVE) {
            ensureChauffeurIsAvailable(request.chauffeurId(), null);
        }

        Affectation affectation = affectationMapper.toEntity(request);
        affectation.setChauffeur(chauffeur);
        affectation.setVehicule(vehicule);
        affectation.setStatut(statutEffectif);

        return affectationMapper.toResponse(affectationRepository.save(affectation));
    }

    @Transactional
    public AffectationResponse update(Long id, AffectationRequest request) {
        Affectation affectation = getAffectationOrThrow(id);
        Chauffeur chauffeur = getChauffeurOrThrow(request.chauffeurId());
        Vehicule vehicule = getVehiculeOrThrow(request.vehiculeId());
        StatutAffectation statutEffectif = request.statut() != null ? request.statut() : affectation.getStatut();

        if (statutEffectif == StatutAffectation.ACTIVE) {
            ensureChauffeurIsAvailable(request.chauffeurId(), id);
        }

        affectationMapper.updateEntityFromRequest(request, affectation);
        affectation.setChauffeur(chauffeur);
        affectation.setVehicule(vehicule);
        affectation.setStatut(statutEffectif);

        return affectationMapper.toResponse(affectationRepository.save(affectation));
    }

    /** Clôture une affectation en cours : date de fin = aujourd'hui, statut = TERMINEE. */
    @Transactional
    public AffectationResponse terminer(Long id) {
        Affectation affectation = getAffectationOrThrow(id);
        affectation.setDateFin(LocalDate.now());
        affectation.setStatut(StatutAffectation.TERMINEE);
        return affectationMapper.toResponse(affectationRepository.save(affectation));
    }

    @Transactional
    public void delete(Long id) {
        affectationRepository.delete(getAffectationOrThrow(id));
    }

    private Affectation getAffectationOrThrow(Long id) {
        return affectationRepository.findById(id)
                .orElseThrow(() -> new RessourcesOperationnellesNotFoundException("Affectation", id));
    }

    private Chauffeur getChauffeurOrThrow(Long chauffeurId) {
        return chauffeurRepository.findById(chauffeurId)
                .orElseThrow(() -> new RessourcesOperationnellesNotFoundException("Chauffeur", chauffeurId));
    }

    /** Vehicule appartient au module ParcAutomobile : on réutilise son exception dédiée. */
    private Vehicule getVehiculeOrThrow(Long vehiculeId) {
        return vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ParcAutomobileNotFoundException("Vehicule", vehiculeId));
    }

    private void ensureChauffeurIsAvailable(Long chauffeurId, Long excludedAffectationId) {
        affectationRepository.findByChauffeurIdAndStatut(chauffeurId, StatutAffectation.ACTIVE)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludedAffectationId)) {
                        throw new RessourcesOperationnellesConflictException(
                                "Ce chauffeur a déjà une affectation active. Terminez-la avant d'en créer une nouvelle.");
                    }
                });
    }
}