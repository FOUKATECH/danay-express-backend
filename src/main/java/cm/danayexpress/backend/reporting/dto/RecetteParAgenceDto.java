package cm.danayexpress.backend.reporting.dto;

import java.math.BigDecimal;

public record RecetteParAgenceDto(
        Long agenceId,
        String agenceNom,
        BigDecimal totalRecettes,
        long nombrePassagers
) {
}
