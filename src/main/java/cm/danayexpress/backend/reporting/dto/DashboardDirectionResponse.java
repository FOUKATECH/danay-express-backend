package cm.danayexpress.backend.reporting.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardDirectionResponse(
        long totalVoyages,
        long totalPassagersTransportes,
        BigDecimal recettesTotales,
        double tauxDisponibiliteParcPourcentage,
        long totalIncidents,
        long totalMaintenances,
        List<RecetteParAgenceDto> recettesParAgence,
        List<RecetteParLigneDto> recettesParLigne,
        List<RecetteParVehiculeDto> topVehiculesPerformants
) {
}
