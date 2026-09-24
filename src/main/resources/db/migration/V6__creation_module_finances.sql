-- Module 6 : Finances (Tarifs, Recettes)
-- Voir CDC section 8.6.

-- ============================================================
-- TARIFS
-- ============================================================
CREATE TABLE tarifs (
                        id              BIGSERIAL PRIMARY KEY,
                        origine_id      BIGINT      NOT NULL REFERENCES agences(id),
                        destination_id  BIGINT      NOT NULL REFERENCES agences(id),
                        type_vehicule   VARCHAR(50) NOT NULL,
                        montant         NUMERIC(12, 2) NOT NULL,
                        date_debut      DATE        NOT NULL,
                        date_fin        DATE,
                        statut          VARCHAR(20) NOT NULL DEFAULT 'ACTIF',
                        created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
                        updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),

                        CONSTRAINT ck_tarifs_statut CHECK (statut IN ('ACTIF', 'INACTIF')),
                        CONSTRAINT ck_tarifs_montant_positif CHECK (montant >= 0),
                        CONSTRAINT ck_tarifs_dates CHECK (date_fin IS NULL OR date_fin >= date_debut),
                        CONSTRAINT ck_tarifs_origine_destination_distinctes CHECK (origine_id <> destination_id)
);

CREATE INDEX idx_tarifs_origine_id ON tarifs(origine_id);
CREATE INDEX idx_tarifs_destination_id ON tarifs(destination_id);
CREATE INDEX idx_tarifs_statut ON tarifs(statut);

-- ============================================================
-- RECETTES
-- ============================================================
CREATE TABLE recettes (
                          id                    BIGSERIAL PRIMARY KEY,
                          mouvement_transit_id  BIGINT      NOT NULL REFERENCES mouvements_transit(id),
                          tarif_id              BIGINT      NOT NULL REFERENCES tarifs(id),
                          montant               NUMERIC(12, 2) NOT NULL,
                          created_at            TIMESTAMPTZ NOT NULL DEFAULT now(),
                          updated_at            TIMESTAMPTZ NOT NULL DEFAULT now(),

                          CONSTRAINT uk_recettes_mouvement_transit UNIQUE (mouvement_transit_id),
                          CONSTRAINT ck_recettes_montant_positif CHECK (montant >= 0)
);

CREATE INDEX idx_recettes_tarif_id ON recettes(tarif_id);