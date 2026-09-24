package cm.danayexpress.backend.administration.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AdministrationNotFoundException extends BusinessException {

    public AdministrationNotFoundException(String entite, Object identifier) {
        super(entite + " introuvable avec l'identifiant " + identifier, HttpStatus.NOT_FOUND);
    }
}
