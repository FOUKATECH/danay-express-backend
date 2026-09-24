package cm.danayexpress.backend.administration.service;

import cm.danayexpress.backend.administration.dto.PermissionResponse;
import cm.danayexpress.backend.administration.dto.RoleResponse;
import cm.danayexpress.backend.administration.entity.Role;
import cm.danayexpress.backend.administration.exception.AdministrationNotFoundException;
import cm.danayexpress.backend.administration.mapper.PermissionMapper;
import cm.danayexpress.backend.administration.mapper.RoleMapper;
import cm.danayexpress.backend.administration.repository.PermissionRepository;
import cm.danayexpress.backend.administration.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    @Override
    public List<RoleResponse> listerRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AdministrationNotFoundException("Rôle", id));
        return roleMapper.toResponse(role);
    }

    @Override
    public List<PermissionResponse> listerPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toResponse)
                .toList();
    }
}
