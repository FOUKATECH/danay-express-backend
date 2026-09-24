package cm.danayexpress.backend.finances.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class FinancesConflictException extends BusinessException {

    public FinancesConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}