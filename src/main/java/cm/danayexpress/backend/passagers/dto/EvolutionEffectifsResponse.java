package cm.danayexpress.backend.passagers.dto;

import cm.danayexpress.backend.exploitation.enums.StatutVoyage;

import java.util.List;

/** Vue d'ensemble de l'évolution des effectifs sur tout le voyage (CDC section 8.5). */
public record EvolutionEffectifsResponse(
        Long voyageId,
        StatutVoyage statutVoyage,
        String agenceDepartNom,
        Integer effectifDepart,
        List<EtapeEffectifResponse> etapes,
        String destinationFinaleNom
) {
}