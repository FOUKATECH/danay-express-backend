package cm.danayexpress.backend.administration.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AdministrationConflictException extends BusinessException {

    public AdministrationConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
