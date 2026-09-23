-- Module 3 : Ressources Opérationnelles (Chauffeurs, Affectations)
-- Voir CDC section 8.3.

-- ============================================================
-- CHAUFFEURS
-- ============================================================
CREATE TABLE chauffeurs (
                            id             BIGSERIAL PRIMARY KEY,
                            nom            VARCHAR(150) NOT NULL,
                            prenom         VARCHAR(150),
                            telephone      VARCHAR(30),
                            numero_permis  VARCHAR(30)  NOT NULL,
                            statut         VARCHAR(20)  NOT NULL DEFAULT 'ACTIF',
                            created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
                            updated_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
                            CONSTRAINT uk_chauffeurs_numero_permis UNIQUE (numero_permis),
                            CONSTRAINT ck_chauffeurs_statut CHECK (statut IN ('ACTIF', 'INACTIF'))
);

-- ============================================================
-- AFFECTATIONS
-- ============================================================
CREATE TABLE affectations (
                              id            BIGSERIAL PRIMARY KEY,
                              chauffeur_id  BIGINT      NOT NULL REFERENCES chauffeurs(id),
                              vehicule_id   BIGINT      NOT NULL REFERENCES vehicules(id),
                              date_debut    DATE        NOT NULL,
                              date_fin      DATE,
                              statut        VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                              created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
                              updated_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
                              CONSTRAINT ck_affectations_statut CHECK (statut IN ('ACTIVE', 'TERMINEE')),
                              CONSTRAINT ck_affectations_dates CHECK (date_fin IS NULL OR date_fin >= date_debut)
);

CREATE INDEX idx_affectations_chauffeur_id ON affectations(chauffeur_id);
CREATE INDEX idx_affectations_vehicule_id ON affectations(vehicule_id);
CREATE INDEX idx_affectations_statut ON affectations(statut);