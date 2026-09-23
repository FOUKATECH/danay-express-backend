package cm.danayexpress.backend.ressourcesoperationnelles.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class RessourcesOperationnellesConflictException extends BusinessException {

    public RessourcesOperationnellesConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}