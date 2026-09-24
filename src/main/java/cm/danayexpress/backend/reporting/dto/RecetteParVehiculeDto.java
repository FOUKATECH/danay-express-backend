package cm.danayexpress.backend.reporting.dto;

import java.math.BigDecimal;

public record RecetteParVehiculeDto(
        Long vehiculeId,
        String immatriculation,
        BigDecimal totalRecettes,
        long nombreVoyagesEffectues
) {
}
