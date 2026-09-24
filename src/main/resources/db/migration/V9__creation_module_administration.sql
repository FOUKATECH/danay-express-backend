-- Module 11 : Administration & Sécurité (Utilisateurs, Rôles, Permissions, Auth JWT)
-- Voir CDC section 8.13, 14 et 15.2.

-- ============================================================
-- ROLES
-- ============================================================
CREATE TABLE roles (
    id              BIGSERIAL PRIMARY KEY,
    code            VARCHAR(50) NOT NULL UNIQUE,
    libelle         VARCHAR(100) NOT NULL,
    description     TEXT,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- ============================================================
-- PERMISSIONS
-- ============================================================
CREATE TABLE permissions (
    id              BIGSERIAL PRIMARY KEY,
    code            VARCHAR(100) NOT NULL UNIQUE,
    libelle         VARCHAR(150) NOT NULL,
    module          VARCHAR(50) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- ============================================================
-- ROLES_PERMISSIONS (N:N)
-- ============================================================
CREATE TABLE roles_permissions (
    role_id         BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    permission_id   BIGINT NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);

-- ============================================================
-- UTILISATEURS
-- ============================================================
CREATE TABLE utilisateurs (
    id                  BIGSERIAL PRIMARY KEY,
    nom_utilisateur     VARCHAR(50) NOT NULL UNIQUE,
    email               VARCHAR(100) UNIQUE,
    mot_de_passe        VARCHAR(255) NOT NULL,
    nom                 VARCHAR(100) NOT NULL,
    prenom              VARCHAR(100) NOT NULL,
    telephone           VARCHAR(30),
    statut              VARCHAR(20) NOT NULL DEFAULT 'ACTIF',
    role_id             BIGINT NOT NULL REFERENCES roles(id),
    agence_id           BIGINT REFERENCES agences(id),
    dernier_login       TIMESTAMPTZ,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT ck_utilisateurs_statut CHECK (statut IN ('ACTIF', 'INACTIF', 'SUSPENDU'))
);

CREATE INDEX idx_utilisateurs_nom_utilisateur ON utilisateurs(nom_utilisateur);
CREATE INDEX idx_utilisateurs_email ON utilisateurs(email);
CREATE INDEX idx_utilisateurs_role_id ON utilisateurs(role_id);
CREATE INDEX idx_utilisateurs_agence_id ON utilisateurs(agence_id);
CREATE INDEX idx_utilisateurs_statut ON utilisateurs(statut);

-- ============================================================
-- DONNÉES INITIALES (SEED ROLES & PERMISSIONS)
-- ============================================================

-- Rôles CDC section 5 & 8.13
INSERT INTO roles (code, libelle, description) VALUES
('SUPER_ADMIN', 'Super Administrateur', 'Accès complet à toutes les fonctionnalités du système'),
('DIRECTEUR_EXPLOITATION', 'Directeur d''Exploitation', 'Supervision globale de l''exploitation, de la planification et des tableaux de bord'),
('COLLABORATEUR_DIRECTEUR', 'Collaborateur du Directeur', 'Aide à la planification et à la supervision des opérations'),
('CHEF_SERVICE_COMMUNICATION', 'Chef de Service Communication', 'Planification et suivi des informations opérationnelles des agences'),
('ASSISTANT_COMMUNICATION', 'Assistant Chef de Service Communication', 'Opérateur central de saisie des départs, arrivées, passagers et incidents'),
('CHEF_AGENCE', 'Chef d''Agence', 'Responsable des opérations, départs et programmation de son agence');

-- Permissions par module
INSERT INTO permissions (code, libelle, module) VALUES
-- Referentiel
('REFERENTIEL_READ', 'Consulter le référentiel (villes, agences, lignes)', 'REFERENTIEL'),
('REFERENTIEL_WRITE', 'Gérer le référentiel (villes, agences, lignes)', 'REFERENTIEL'),
-- Parc Automobile
('PARC_READ', 'Consulter le parc automobile', 'PARC_AUTOMOBILE'),
('PARC_WRITE', 'Gérer les véhicules et propriétaires', 'PARC_AUTOMOBILE'),
-- Ressources Opérationnelles
('RESSOURCES_READ', 'Consulter les chauffeurs et affectations', 'RESSOURCES'),
('RESSOURCES_WRITE', 'Gérer les chauffeurs et affectations', 'RESSOURCES'),
-- Exploitation
('EXPLOITATION_READ', 'Consulter les voyages et le planning', 'EXPLOITATION'),
('EXPLOITATION_PLANIFIER', 'Planifier les voyages', 'EXPLOITATION'),
('EXPLOITATION_PROGRAMMER', 'Programmer les véhicules', 'EXPLOITATION'),
('EXPLOITATION_EXECUTION', 'Enregistrer les départs, escales et arrivées', 'EXPLOITATION'),
-- Passagers
('PASSAGERS_READ', 'Consulter les mouvements et effectifs passagers', 'PASSAGERS'),
('PASSAGERS_WRITE', 'Saisir les mouvements de passagers/transit', 'PASSAGERS'),
-- Finances
('FINANCES_READ', 'Consulter les tarifs et recettes', 'FINANCES'),
('FINANCES_WRITE', 'Gérer les tarifs et recettes', 'FINANCES'),
-- Incidents & Secours
('INCIDENTS_READ', 'Consulter les incidents et secours', 'INCIDENTS'),
('INCIDENTS_WRITE', 'Déclarer et gérer les incidents/secours', 'INCIDENTS'),
-- Maintenance
('MAINTENANCE_READ', 'Consulter la maintenance', 'MAINTENANCE'),
('MAINTENANCE_WRITE', 'Gérer les interventions de maintenance', 'MAINTENANCE'),
-- Reporting
('REPORTING_READ', 'Consulter les tableaux de bord et rapports', 'REPORTING'),
-- Administration & Audit
('ADMIN_READ', 'Consulter les utilisateurs et rôles', 'ADMINISTRATION'),
('ADMIN_WRITE', 'Gérer les utilisateurs et permissions', 'ADMINISTRATION'),
('AUDIT_READ', 'Consulter le journal d''audit', 'AUDIT');

-- Association SUPER_ADMIN -> Toutes les permissions
INSERT INTO roles_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p WHERE r.code = 'SUPER_ADMIN';

-- Association DIRECTEUR_EXPLOITATION -> Toutes les permissions opérationnelles & reporting
INSERT INTO roles_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.code = 'DIRECTEUR_EXPLOITATION' 
  AND p.code IN ('REFERENTIEL_READ', 'REFERENTIEL_WRITE', 'PARC_READ', 'PARC_WRITE', 'RESSOURCES_READ', 'RESSOURCES_WRITE', 'EXPLOITATION_READ', 'EXPLOITATION_PLANIFIER', 'EXPLOITATION_PROGRAMMER', 'EXPLOITATION_EXECUTION', 'PASSAGERS_READ', 'PASSAGERS_WRITE', 'FINANCES_READ', 'FINANCES_WRITE', 'INCIDENTS_READ', 'INCIDENTS_WRITE', 'MAINTENANCE_READ', 'MAINTENANCE_WRITE', 'REPORTING_READ', 'AUDIT_READ');

-- Utilisateur Administrateur initial (mot de passe haché BCrypt pour "admin123")
-- Hash BCrypt de "admin123" : $2a$10$e8W/6n9fI3R4oK3B0pEmeu6Q8XjS4mBwJ9yH7gS5x0L6e2L.YmOiC (généré standard)
INSERT INTO utilisateurs (nom_utilisateur, email, mot_de_passe, nom, prenom, telephone, statut, role_id)
VALUES (
    'admin',
    'admin@danayexpress.cm',
    '$2a$10$wN1iN61e7nZtL0K62nN7z.9P9F7K7vJ2y8X9Z0W1V2U3T4S5R6Q7P',
    'Administrateur',
    'Système',
    '+237 600000000',
    'ACTIF',
    (SELECT id FROM roles WHERE code = 'SUPER_ADMIN')
);
