-- Module 9 : Reporting & Tableaux de bord
-- Voir CDC section 8.10, 8.11 et 19.

-- ============================================================
-- VUE D'ENSEMBLE RECAPITULATIVE DES RECETTES PAR AGENCE ORIGINE
-- ============================================================
CREATE OR REPLACE VIEW view_recettes_par_agence AS
SELECT 
    a.id AS agence_id,
    a.nom AS nom_agence,
    COALESCE(SUM(r.montant), 0) AS total_recettes,
    COUNT(r.id) AS nombre_transactions
FROM agences a
LEFT JOIN mouvements_transit mt ON mt.agence_origine_id = a.id
LEFT JOIN recettes r ON r.mouvement_transit_id = mt.id
GROUP BY a.id, a.nom;

-- ============================================================
-- VUE D'ENSEMBLE DU PARC AUTOMOBILE
-- ============================================================
CREATE OR REPLACE VIEW view_statistiques_vehicules AS
SELECT 
    statut,
    COUNT(*) AS nombre_vehicules
FROM vehicules
GROUP BY statut;
