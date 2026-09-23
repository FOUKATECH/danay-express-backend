package cm.danayexpress.backend.ressourcesoperationnelles.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class RessourcesOperationnellesNotFoundException extends BusinessException {

    public RessourcesOperationnellesNotFoundException(String entite, Long id) {
        super(entite + " introuvable avec l'id " + id, HttpStatus.NOT_FOUND);
    }
}