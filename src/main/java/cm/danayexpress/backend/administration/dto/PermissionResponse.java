package cm.danayexpress.backend.administration.dto;

public record PermissionResponse(
        Long id,
        String code,
        String libelle,
        String module
) {
}
