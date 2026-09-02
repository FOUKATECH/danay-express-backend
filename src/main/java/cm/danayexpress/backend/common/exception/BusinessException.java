package cm.danayexpress.backend.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception métier de base. Chaque module définit ses propres exceptions
 * spécifiques dans son package "exception" en héritant de celle-ci.
 *
 * Exemple (module exploitation) :
 * <pre>
 *   public class VoyageException extends BusinessException {
 *       public VoyageException(String message) {
 *           super(message, HttpStatus.CONFLICT);
 *       }
 *   }
 * </pre>
 */
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
