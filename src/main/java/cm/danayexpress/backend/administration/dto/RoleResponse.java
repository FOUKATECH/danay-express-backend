package cm.danayexpress.backend.administration.dto;

import java.util.Set;

public record RoleResponse(
        Long id,
        String code,
        String libelle,
        String description,
        Set<PermissionResponse> permissions
) {
}
