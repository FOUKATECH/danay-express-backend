package cm.danayexpress.backend.finances.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class FinancesNotFoundException extends BusinessException {

    public FinancesNotFoundException(String entite, Long id) {
        super(entite + " introuvable avec l'id " + id, HttpStatus.NOT_FOUND);
    }
}