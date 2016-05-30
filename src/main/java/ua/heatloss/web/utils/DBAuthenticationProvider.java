package ua.heatloss.web.utils;

import ua.heatloss.domain.user.User;
import ua.heatloss.services.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;


public class DBAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(DBAuthenticationProvider.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = null;
        try {
            user = userService.getUserByEmail(email);
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return null;
            }
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority(user.getRole()
                    .toString()));
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    email, password, grantedAuths);
            return auth;
        } catch (NoResultException ex) {
            LOG.debug("Email not found in DB");
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);

    }
}
