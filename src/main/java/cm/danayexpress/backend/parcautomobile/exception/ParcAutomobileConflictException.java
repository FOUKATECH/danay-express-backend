package cm.danayexpress.backend.parcautomobile.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ParcAutomobileConflictException extends BusinessException {

    public ParcAutomobileConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}