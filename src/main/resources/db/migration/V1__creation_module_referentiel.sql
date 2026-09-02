-- Module 1 : Référentiel (Agence, Ville, Ligne, Sens, ÉtapeItinéraire)
-- Voir CDC section 6, 7, 8.1 et règles métier RM-07, RM-08, RM-09.

-- ============================================================
-- VILLES
-- ============================================================
CREATE TABLE villes (
                        id          BIGSERIAL PRIMARY KEY,
                        nom         VARCHAR(150) NOT NULL,
                        region      VARCHAR(150),
                        created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
                        updated_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
                        CONSTRAINT uk_villes_nom UNIQUE (nom)
);

-- ============================================================
-- AGENCES
-- ============================================================
CREATE TABLE agences (
                         id           BIGSERIAL PRIMARY KEY,
                         code         VARCHAR(20)  NOT NULL,
                         nom          VARCHAR(150) NOT NULL,
                         ville_id     BIGINT       NOT NULL REFERENCES villes(id),
                         adresse      VARCHAR(255),
                         telephone    VARCHAR(30),
                         responsable  VARCHAR(150),
                         statut       VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
                         created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
                         updated_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
                         CONSTRAINT uk_agences_code UNIQUE (code),
                         CONSTRAINT ck_agences_statut CHECK (statut IN ('ACTIVE', 'INACTIVE'))
);

CREATE INDEX idx_agences_ville_id ON agences(ville_id);

-- ============================================================
-- LIGNES (corridors)
-- ============================================================
CREATE TABLE lignes (
                        id           BIGSERIAL PRIMARY KEY,
                        code         VARCHAR(20)  NOT NULL,
                        nom          VARCHAR(150) NOT NULL,
                        description  TEXT,
                        statut       VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
                        created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
                        updated_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
                        CONSTRAINT uk_lignes_code UNIQUE (code),
                        CONSTRAINT ck_lignes_statut CHECK (statut IN ('ACTIVE', 'INACTIVE'))
);

-- ============================================================
-- SENS (RM-09 : une ligne peut être parcourue dans les deux sens)
-- ============================================================
CREATE TABLE sens (
                      id                 BIGSERIAL PRIMARY KEY,
                      ligne_id           BIGINT      NOT NULL REFERENCES lignes(id),
                      code               VARCHAR(10) NOT NULL,
                      agence_depart_id   BIGINT      NOT NULL REFERENCES agences(id),
                      agence_arrivee_id  BIGINT      NOT NULL REFERENCES agences(id),
                      statut             VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                      created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
                      updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
                      CONSTRAINT uk_sens_ligne_code UNIQUE (ligne_id, code),
                      CONSTRAINT ck_sens_code CHECK (code IN ('ALLER', 'RETOUR')),
                      CONSTRAINT ck_sens_statut CHECK (statut IN ('ACTIVE', 'INACTIVE')),
                      CONSTRAINT ck_sens_depart_arrivee_distincts CHECK (agence_depart_id <> agence_arrivee_id)
);

CREATE INDEX idx_sens_ligne_id ON sens(ligne_id);
CREATE INDEX idx_sens_agence_depart_id ON sens(agence_depart_id);
CREATE INDEX idx_sens_agence_arrivee_id ON sens(agence_arrivee_id);

-- ============================================================
-- ÉTAPES D'ITINÉRAIRE (RM-07 : agences ordonnées selon ligne + sens)
-- ============================================================
CREATE TABLE etapes_itineraire (
                                   id          BIGSERIAL PRIMARY KEY,
                                   sens_id     BIGINT      NOT NULL REFERENCES sens(id),
                                   agence_id   BIGINT      NOT NULL REFERENCES agences(id),
                                   ordre       INTEGER     NOT NULL,
                                   created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
                                   updated_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    -- Un même sens ne peut pas avoir deux agences à la même position...
                                   CONSTRAINT uk_etapes_sens_ordre UNIQUE (sens_id, ordre),
    -- ... ni la même agence à deux positions différentes.
                                   CONSTRAINT uk_etapes_sens_agence UNIQUE (sens_id, agence_id),
                                   CONSTRAINT ck_etapes_ordre_positif CHECK (ordre > 0)
);

CREATE INDEX idx_etapes_sens_id ON etapes_itineraire(sens_id);
CREATE INDEX idx_etapes_agence_id ON etapes_itineraire(agence_id);