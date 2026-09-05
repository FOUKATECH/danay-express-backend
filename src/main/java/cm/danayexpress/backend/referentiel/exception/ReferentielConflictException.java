package cm.danayexpress.backend.referentiel.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * Levée en cas de violation d'une règle d'unicité ou de cohérence du
 * référentiel (ex: code d'agence déjà utilisé, ordre déjà pris dans un
 * sens, agence de départ = agence d'arrivée...).
 */
public class ReferentielConflictException extends BusinessException {

    public ReferentielConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}