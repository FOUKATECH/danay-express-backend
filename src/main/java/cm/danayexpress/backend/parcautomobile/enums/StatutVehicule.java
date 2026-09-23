package cm.danayexpress.backend.parcautomobile.enums;

/**
 * Statuts d'un véhicule (CDC section 8.2). Le cycle de vie détaillé
 * (transitions autorisées) sera implémenté dans le module Exploitation,
 * qui pilote les changements de statut liés aux voyages.
 */
public enum StatutVehicule {
    DISPONIBLE,
    PROGRAMME,
    EN_VOYAGE,
    EN_PANNE,
    EN_SECOURS,
    EN_MAINTENANCE,
    IMMOBILISE
}