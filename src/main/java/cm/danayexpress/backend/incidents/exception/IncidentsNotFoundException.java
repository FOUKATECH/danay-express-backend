package cm.danayexpress.backend.incidents.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class IncidentsNotFoundException extends BusinessException {

    public IncidentsNotFoundException(String entite, Long id) {
        super(entite + " introuvable avec l'id " + id, HttpStatus.NOT_FOUND);
    }
}
