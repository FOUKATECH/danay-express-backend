package cm.danayexpress.backend.parcautomobile.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ParcAutomobileNotFoundException extends BusinessException {

    public ParcAutomobileNotFoundException(String entite, Long id) {
        super(entite + " introuvable avec l'id " + id, HttpStatus.NOT_FOUND);
    }
}