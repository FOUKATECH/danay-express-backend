-- Module 2 : Parc Automobile (Véhicules, Propriétaires)
-- Voir CDC section 8.2.

-- ============================================================
-- PROPRIETAIRES
-- ============================================================
CREATE TABLE proprietaires (
                               id          BIGSERIAL PRIMARY KEY,
                               nom         VARCHAR(150) NOT NULL,
                               telephone   VARCHAR(30),
                               adresse     VARCHAR(255),
                               created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
                               updated_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- ============================================================
-- VEHICULES
-- ============================================================
CREATE TABLE vehicules (
                           id               BIGSERIAL PRIMARY KEY,
                           immatriculation  VARCHAR(20)  NOT NULL,
                           type             VARCHAR(50),
                           capacite         INTEGER      NOT NULL,
                           proprietaire_id  BIGINT       NOT NULL REFERENCES proprietaires(id),
                           agence_id        BIGINT       NOT NULL REFERENCES agences(id),
                           statut           VARCHAR(20)  NOT NULL DEFAULT 'DISPONIBLE',
                           created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
                           updated_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
                           CONSTRAINT uk_vehicules_immatriculation UNIQUE (immatriculation),
                           CONSTRAINT ck_vehicules_capacite_positive CHECK (capacite > 0),
                           CONSTRAINT ck_vehicules_statut CHECK (statut IN (
                                                                            'DISPONIBLE', 'PROGRAMME', 'EN_VOYAGE', 'EN_PANNE',
                                                                            'EN_SECOURS', 'EN_MAINTENANCE', 'IMMOBILISE'
                               ))
);

CREATE INDEX idx_vehicules_proprietaire_id ON vehicules(proprietaire_id);
CREATE INDEX idx_vehicules_agence_id ON vehicules(agence_id);
CREATE INDEX idx_vehicules_statut ON vehicules(statut);