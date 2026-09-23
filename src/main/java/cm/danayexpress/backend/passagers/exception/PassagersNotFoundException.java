package cm.danayexpress.backend.passagers.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PassagersNotFoundException extends BusinessException {

    public PassagersNotFoundException(String entite, Long id) {
        super(entite + " introuvable avec l'id " + id, HttpStatus.NOT_FOUND);
    }
}