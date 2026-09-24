package cm.danayexpress.backend.security;

import cm.danayexpress.backend.administration.entity.Permission;
import cm.danayexpress.backend.administration.entity.Utilisateur;
import cm.danayexpress.backend.administration.enums.StatutUtilisateur;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Utilisateur utilisateur;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        Set<GrantedAuthority> auths = new HashSet<>();
        // Rôle principal (ex: ROLE_SUPER_ADMIN, ROLE_CHEF_AGENCE)
        if (utilisateur.getRole() != null) {
            auths.add(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().getCode()));

            // Permissions fines (ex: EXPLOITATION_PROGRAMMER, FINANCES_READ)
            if (utilisateur.getRole().getPermissions() != null) {
                for (Permission perm : utilisateur.getRole().getPermissions()) {
                    auths.add(new SimpleGrantedAuthority(perm.getCode()));
                }
            }
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return utilisateur.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return utilisateur.getNomUtilisateur();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return utilisateur.getStatut() != StatutUtilisateur.SUSPENDU;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return utilisateur.getStatut() == StatutUtilisateur.ACTIF;
    }
}
