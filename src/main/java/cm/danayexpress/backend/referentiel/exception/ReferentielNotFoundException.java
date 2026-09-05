package cm.danayexpress.backend.referentiel.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * Levée quand une ressource du référentiel (Ville, Agence, Ligne, Sens,
 * EtapeItineraire) est demandée par son id mais n'existe pas.
 */
public class ReferentielNotFoundException extends BusinessException {

    public ReferentielNotFoundException(String entite, Long id) {
        super(entite + " introuvable avec l'id " + id, HttpStatus.NOT_FOUND);
    }
}