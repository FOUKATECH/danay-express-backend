package cm.danayexpress.backend.reporting.service;

import cm.danayexpress.backend.reporting.dto.DashboardDirectionResponse;
import cm.danayexpress.backend.reporting.dto.DashboardOperationnelResponse;

import java.time.Instant;

/**
 * Service pour l'agrégation des indicateurs et tableaux de bord (CDC section 8.10 & 19).
 */
public interface DashboardService {

    DashboardOperationnelResponse getDashboardOperationnel();

    DashboardDirectionResponse getDashboardDirection(Instant debut, Instant fin);
}
