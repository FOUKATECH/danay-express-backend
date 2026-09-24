package cm.danayexpress.backend.administration.service;

import cm.danayexpress.backend.administration.dto.ChangePasswordRequest;
import cm.danayexpress.backend.administration.dto.LoginRequest;
import cm.danayexpress.backend.administration.dto.LoginResponse;
import cm.danayexpress.backend.administration.dto.UserResponse;

/**
 * Service gérant l'authentification et les sessions (CDC section 14).
 */
public interface AuthService {

    LoginResponse login(LoginRequest request);

    UserResponse getCurrentUser();

    void changePassword(ChangePasswordRequest request);
}
