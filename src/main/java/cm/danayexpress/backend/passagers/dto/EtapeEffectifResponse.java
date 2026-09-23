package cm.danayexpress.backend.passagers.dto;

import java.time.LocalTime;

/** Une étape dans la timeline d'évolution des effectifs d'un voyage. */
public record EtapeEffectifResponse(
        String agenceNom,
        Integer ordre,
        LocalTime heureArrivee,
        Integer passagersDescendus,
        Integer passagersMontes,
        Integer effectifABord
) {
}