-- Module 10 : Journalisation & Audit
-- Voir CDC section 8.12.

-- ============================================================
-- JOURNAUX D'AUDIT
-- ============================================================
CREATE TABLE journaux_audit (
    id                  BIGSERIAL PRIMARY KEY,
    nom_utilisateur     VARCHAR(50)     NOT NULL,
    action              VARCHAR(50)     NOT NULL,
    module              VARCHAR(50)     NOT NULL,
    element_id          VARCHAR(100),
    description         TEXT            NOT NULL,
    ancienne_valeur     TEXT,
    nouvelle_valeur     TEXT,
    adresse_ip          VARCHAR(45),
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT now()
);

CREATE INDEX idx_journaux_audit_nom_utilisateur ON journaux_audit(nom_utilisateur);
CREATE INDEX idx_journaux_audit_module ON journaux_audit(module);
CREATE INDEX idx_journaux_audit_action ON journaux_audit(action);
CREATE INDEX idx_journaux_audit_created_at ON journaux_audit(created_at);
CREATE INDEX idx_journaux_audit_element_id ON journaux_audit(element_id);
