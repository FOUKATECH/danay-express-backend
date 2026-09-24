-- Module 7 : Incidents & Véhicules de Secours
-- Voir CDC section 8.7 et 17.

-- ============================================================
-- INCIDENTS
-- ============================================================
CREATE TABLE incidents (
    id                          BIGSERIAL PRIMARY KEY,
    vehicule_id                 BIGINT          NOT NULL REFERENCES vehicules(id),
    voyage_id                   BIGINT          REFERENCES voyages(id),
    agence_id                   BIGINT          REFERENCES agences(id),
    localisation_declaree       VARCHAR(255),
    date_heure_incident        TIMESTAMPTZ     NOT NULL DEFAULT now(),
    type_incident               VARCHAR(50)     NOT NULL,
    gravite                     VARCHAR(20)     NOT NULL,
    description                 TEXT,
    nombre_passagers_concernes  INT,
    statut                      VARCHAR(30)     NOT NULL DEFAULT 'SIGNALE',
    created_at                  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at                  TIMESTAMPTZ     NOT NULL DEFAULT now(),

    CONSTRAINT ck_incidents_statut CHECK (statut IN ('SIGNALE', 'EN_COURS', 'SECOURS_AFFECTE', 'RESOLU', 'CLOTURE')),
    CONSTRAINT ck_incidents_gravite CHECK (gravite IN ('FAIBLE', 'MOYENNE', 'ELEVEE', 'CRITIQUE')),
    CONSTRAINT ck_incidents_type CHECK (type_incident IN ('PANNE_MECANIQUE', 'ACCIDENT', 'CREVAISON', 'INCIDENT_PASSAGER', 'METEO_OBSTACLE', 'AUTRE')),
    CONSTRAINT ck_incidents_passagers CHECK (nombre_passagers_concernes IS NULL OR nombre_passagers_concernes >= 0)
);

CREATE INDEX idx_incidents_vehicule_id ON incidents(vehicule_id);
CREATE INDEX idx_incidents_voyage_id ON incidents(voyage_id);
CREATE INDEX idx_incidents_agence_id ON incidents(agence_id);
CREATE INDEX idx_incidents_statut ON incidents(statut);
CREATE INDEX idx_incidents_gravite ON incidents(gravite);

-- ============================================================
-- INTERVENTIONS DE SECOURS
-- ============================================================
CREATE TABLE interventions_secours (
    id                          BIGSERIAL PRIMARY KEY,
    incident_id                 BIGINT          NOT NULL REFERENCES incidents(id),
    vehicule_secours_id         BIGINT          NOT NULL REFERENCES vehicules(id),
    chauffeur_secours_id        BIGINT          REFERENCES chauffeurs(id),
    date_heure_affectation      TIMESTAMPTZ     NOT NULL DEFAULT now(),
    date_heure_depart           TIMESTAMPTZ,
    date_heure_prise_en_charge  TIMESTAMPTZ,
    date_heure_resolution       TIMESTAMPTZ,
    statut                      VARCHAR(30)     NOT NULL DEFAULT 'AFFECTE',
    observations                TEXT,
    created_at                  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at                  TIMESTAMPTZ     NOT NULL DEFAULT now(),

    CONSTRAINT ck_interventions_secours_statut CHECK (statut IN ('AFFECTE', 'EN_ROUTE', 'PRIS_EN_CHARGE', 'TERMINE', 'ANNULE'))
);

CREATE INDEX idx_interventions_secours_incident_id ON interventions_secours(incident_id);
CREATE INDEX idx_interventions_secours_vehicule_secours_id ON interventions_secours(vehicule_secours_id);
CREATE INDEX idx_interventions_secours_statut ON interventions_secours(statut);
