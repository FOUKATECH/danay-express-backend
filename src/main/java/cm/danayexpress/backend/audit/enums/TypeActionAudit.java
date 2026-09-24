package cm.danayexpress.backend.audit.enums;

/**
 * Types d'actions journalisées dans le système (CDC section 8.12).
 */
public enum TypeActionAudit {
    CREATION,
    MODIFICATION,
    SUPPRESSION,
    PROGRAMMATION,
    DEPART,
    ESCALE,
    ARRIVEE,
    INCIDENT_SIGNALE,
    SECOURS_AFFECTE,
    MAINTENANCE_DECLAREE,
    MAINTENANCE_CLOTUREE,
    CONNEXION,
    DECONNEXION,
    REINITIALISATION_MDP,
    AUTRE
}
