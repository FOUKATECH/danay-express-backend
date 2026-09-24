package cm.danayexpress.backend.maintenance.exception;

import cm.danayexpress.backend.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MaintenanceNotFoundException extends BusinessException {

    public MaintenanceNotFoundException(String entite, Long id) {
        super(entite + " introuvable avec l'id " + id, HttpStatus.NOT_FOUND);
    }
}
