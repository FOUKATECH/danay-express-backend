package cm.danayexpress.backend.reporting.dto;

import java.math.BigDecimal;

public record RecetteParLigneDto(
        Long ligneId,
        String ligneNom,
        BigDecimal totalRecettes,
        long nombreVoyages
) {
}
