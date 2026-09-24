package cm.danayexpress.backend.maintenance.dto;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.Instant;

public record CloturerMaintenanceRequest(

        String travauxRealises,

        @DecimalMin(value = "0", message = "Le coût total doit être supérieur ou égal à 0")
        BigDecimal coutTotal,

        Instant dateSortieReelle
) {
}
