package cm.danayexpress.backend.referentiel.enums;

/**
 * Sens de circulation sur une ligne (CDC section 6.2, RM-09).
 * Une même ligne est parcourue dans les deux sens, chacun ayant son
 * propre ordre d'agences (voir EtapeItineraire).
 */
public enum SensCode {
    ALLER,
    RETOUR
}