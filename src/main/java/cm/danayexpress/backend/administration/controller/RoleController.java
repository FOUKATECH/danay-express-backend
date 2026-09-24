package cm.danayexpress.backend.administration.controller;

import cm.danayexpress.backend.administration.dto.PermissionResponse;
import cm.danayexpress.backend.administration.dto.RoleResponse;
import cm.danayexpress.backend.administration.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/administration")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> listerRoles() {
        return ResponseEntity.ok(roleService.listerRoles());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionResponse>> listerPermissions() {
        return ResponseEntity.ok(roleService.listerPermissions());
    }
}
