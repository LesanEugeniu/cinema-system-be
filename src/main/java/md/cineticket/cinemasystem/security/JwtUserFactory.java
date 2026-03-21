package md.cineticket.cinemasystem.security;

import md.cineticket.cinemasystem.model.Role;
import md.cineticket.cinemasystem.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getPassword(),
                user.getEmail(),
                true,
                mapToGrantedAuthority(user.getRole())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
