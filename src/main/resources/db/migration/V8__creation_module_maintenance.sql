-- Module 8 : Maintenance des véhicules
-- Voir CDC section 8.8 et 17.

-- ============================================================
-- INTERVENTIONS DE MAINTENANCE
-- ============================================================
CREATE TABLE interventions_maintenance (
    id                          BIGSERIAL PRIMARY KEY,
    vehicule_id                 BIGINT          NOT NULL REFERENCES vehicules(id),
    incident_id                 BIGINT          REFERENCES incidents(id),
    type_intervention           VARCHAR(50)     NOT NULL,
    statut                      VARCHAR(30)     NOT NULL DEFAULT 'EN_COURS',
    description_panne_motif     TEXT            NOT NULL,
    travaux_realises            TEXT,
    garage_prestataire          VARCHAR(255),
    cout_total                  NUMERIC(12, 2),
    date_entree                 TIMESTAMPTZ     NOT NULL DEFAULT now(),
    date_sortie_prevue          TIMESTAMPTZ,
    date_sortie_reelle          TIMESTAMPTZ,
    created_at                  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at                  TIMESTAMPTZ     NOT NULL DEFAULT now(),

    CONSTRAINT ck_maintenances_statut CHECK (statut IN ('PLANIFIEE', 'EN_COURS', 'TERMINEE', 'ANNULEE')),
    CONSTRAINT ck_maintenances_type CHECK (type_intervention IN ('PREVENTIVE', 'CORRECTIVE', 'REVISION_ROUTINE', 'DIAGNOSTIC', 'AUTRE')),
    CONSTRAINT ck_maintenances_cout_positif CHECK (cout_total IS NULL OR cout_total >= 0),
    CONSTRAINT ck_maintenances_dates CHECK (date_sortie_reelle IS NULL OR date_sortie_reelle >= date_entree)
);

CREATE INDEX idx_maintenances_vehicule_id ON interventions_maintenance(vehicule_id);
CREATE INDEX idx_maintenances_incident_id ON interventions_maintenance(incident_id);
CREATE INDEX idx_maintenances_statut ON interventions_maintenance(statut);
CREATE INDEX idx_maintenances_type ON interventions_maintenance(type_intervention);
