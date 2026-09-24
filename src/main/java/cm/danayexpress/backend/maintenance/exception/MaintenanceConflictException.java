package cm.danayexpress.backend.maintenance.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MaintenanceConflictException extends BusinessException {

    public MaintenanceConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
