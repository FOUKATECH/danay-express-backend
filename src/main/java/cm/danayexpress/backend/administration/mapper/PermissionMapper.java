package cm.danayexpress.backend.administration.mapper;

import cm.danayexpress.backend.administration.dto.PermissionResponse;
import cm.danayexpress.backend.administration.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionResponse toResponse(Permission permission);
}
