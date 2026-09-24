package cm.danayexpress.backend.reporting.dto;

import cm.danayexpress.backend.exploitation.dto.VoyageResponse;

import java.util.List;
import java.util.Map;

public record DashboardOperationnelResponse(
        long totalVehicules,
        Map<String, Long> vehiculesParStatut,
        long voyagesProgrammesCount,
        long voyagesEnCoursCount,
        long voyagesTerminesCount,
        long incidentsActifsCount,
        long secoursMobilisesCount,
        List<VoyageResponse> prochainsDeparts,
        List<VoyageResponse> dernieresArrivees
) {
}
