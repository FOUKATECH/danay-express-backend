package cm.danayexpress.backend.administration.mapper;

import cm.danayexpress.backend.administration.dto.RoleResponse;
import cm.danayexpress.backend.administration.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {

    RoleResponse toResponse(Role role);
}
