package cm.danayexpress.backend.reporting.dto;

import cm.danayexpress.backend.parcautomobile.enums.StatutVehicule;

public record StatutVehiculeCountDto(
        StatutVehicule statut,
        long count
) {
}
