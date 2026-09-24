package cm.danayexpress.backend.administration.service;

import cm.danayexpress.backend.administration.dto.PermissionResponse;
import cm.danayexpress.backend.administration.dto.RoleResponse;

import java.util.List;

public interface RoleService {

    List<RoleResponse> listerRoles();

    RoleResponse getRoleById(Long id);

    List<PermissionResponse> listerPermissions();
}
