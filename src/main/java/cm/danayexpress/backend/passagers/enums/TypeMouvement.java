package cm.danayexpress.backend.passagers.enums;

/**
 * Type de mouvement de passagers (CDC section 8.5).
 * MONTEE : l'agence d'origine est toujours l'agence où le mouvement a lieu.
 * DESCENTE : l'agence d'origine est celle où le passager était monté (peut différer).
 */
public enum TypeMouvement {
    MONTEE,
    DESCENTE
}