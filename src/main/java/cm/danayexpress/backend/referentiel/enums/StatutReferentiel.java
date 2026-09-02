package cm.danayexpress.backend.referentiel.enums;

/**
 * Statut générique utilisé par Agence, Ligne et Sens.
 * Une entité INACTIVE n'est plus proposée lors de la planification
 * de nouveaux voyages, mais reste consultable pour l'historique.
 */
public enum StatutReferentiel {
    ACTIVE,
    INACTIVE
}