package cm.danayexpress.backend.incidents.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class IncidentsConflictException extends BusinessException {

    public IncidentsConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
