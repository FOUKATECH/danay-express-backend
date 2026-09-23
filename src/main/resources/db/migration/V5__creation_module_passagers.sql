-- Module 5 : Passagers et Transit
-- Voir CDC section 8.5 et sa règle métier (conservation de l'origine du mouvement).

CREATE TABLE mouvements_transit (
                                    id                  BIGSERIAL PRIMARY KEY,
                                    voyage_id           BIGINT      NOT NULL REFERENCES voyages(id),
                                    escale_id           BIGINT      REFERENCES escales(id),
                                    agence_origine_id   BIGINT      NOT NULL REFERENCES agences(id),
                                    agence_mouvement_id BIGINT      NOT NULL REFERENCES agences(id),
                                    type_mouvement      VARCHAR(20) NOT NULL,
                                    nombre_passagers    INTEGER     NOT NULL,
                                    created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
                                    updated_at          TIMESTAMPTZ NOT NULL DEFAULT now(),

                                    CONSTRAINT ck_mouvements_type CHECK (type_mouvement IN ('MONTEE', 'DESCENTE')),
                                    CONSTRAINT ck_mouvements_nombre_positif CHECK (nombre_passagers > 0)
);

CREATE INDEX idx_mouvements_voyage_id ON mouvements_transit(voyage_id);
CREATE INDEX idx_mouvements_escale_id ON mouvements_transit(escale_id);
CREATE INDEX idx_mouvements_agence_origine_id ON mouvements_transit(agence_origine_id);
CREATE INDEX idx_mouvements_agence_mouvement_id ON mouvements_transit(agence_mouvement_id);